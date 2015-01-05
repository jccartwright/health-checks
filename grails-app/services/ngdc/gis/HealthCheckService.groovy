package ngdc.gis

import grails.transaction.Transactional
import java.security.MessageDigest
import groovy.json.JsonSlurper


@Transactional
class HealthCheckService {

    def run(HealthCheck healthCheck) {

        def url = healthCheck.constructUrl()
        log.debug "checking ${url}..."
        def result = [:]
        try {
            def startTime = new Date()
            def conn = url.openConnection()
            conn.setReadTimeout(60*1000)
            conn.setConnectTimeout(5*1000)
            def bytes = conn.inputStream.bytes
            def endTime = new Date()
            result.elapsedTime = endTime.time - startTime.time

            if (conn.responseCode != 200) {
                result.success = false
                log.warn "ERROR on ${url}. ${conn.responseCode}: ${conn.responseMessage}"
            } else {
                result.success = true
                result.size = bytes.size()
                result.md5 = getMd5(bytes)
                log.debug "read ${bytes.size()} bytes from ${url}"
            }
        } catch (SocketTimeoutException e) {
            result.success = false
            log.warn "SocketTimeout reading URL ${url}"
        }

        //update HealthCheck state
        if (healthCheck.responseChecksum && result.success) {
            if (healthCheck.responseChecksum == result.md5) {
                healthCheck.lastSuccess = true
            } else {
                log.warn("successful URL connection but failed to match MD5")
                healthCheck.lastSuccess = false
            }
        } else {
            healthCheck.lastSuccess = result.success
        }
        if (result.elapsedTime) {
            healthCheck.lastResponseTime = result.elapsedTime
        }
        healthCheck.checkCount++
        return result
    }

    def getMd5(bytes) {
        MessageDigest digest = MessageDigest.getInstance("MD5")
        digest.update(bytes);
        new BigInteger(1, digest.digest()).toString(16).padLeft(32, '0')
    }


    def jsonSlurper = new JsonSlurper()
    def load(host) {
        def mapservices = readArcgisServiceDirectory("http://${host}/arcgis/rest/services")

        def msg = ""
        mapservices.each {
            //if (! Mapservice.findByName(it)) {
                msg += "adding Mapservice ${it}...\n"
              //  new Mapservice(name:it).save()
            //} else {
            //    log.debug "skipping ${it}..."
            //}
        }
        if (msg) {
            return msg
        } else {
            return "no new mapservices to add"
        }
    }

    def readArcgisServiceDirectory(baseUrl) {
        def url = new URL("${baseUrl}?f=json")
        def conn = url.openConnection()

        if (conn.responseCode != 200) {
            log.warn "ERROR on ${baseUrl}. ${conn.responseCode}: ${conn.responseMessage}"
            return
        }

        def result = jsonSlurper.parseText( conn.content.text )

        def mapservices = result.services.findAll {
            it.type == 'MapServer'  //ignore GeocodeServer, ImageServer
        }.name

        result.folders.each {
            def content = readArcgisServiceDirectory("${baseUrl}/${it}")
            if (content) {
                mapservices += content
            }
        }
        return mapservices
    }
}

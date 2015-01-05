package ngdc.gis

import grails.transaction.Transactional
import java.security.MessageDigest

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
}

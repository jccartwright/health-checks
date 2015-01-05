package ngdc.gis

import static org.springframework.http.HttpStatus.*


class HealthCheckController {
    def scaffold = true
    def healthCheckService

    //def index() { }

    def testMe() {
        /*
        def check = HealthCheck.get(1)
        println check.tags
        def mapsChecks = HealthCheck.findAllByTag("maps1") // Also takes params eg [max:5]
        println mapsChecks
        def count = HealthCheck.countByTag("arcgis")
        */
        //println HealthCheck.findByCheckInterval('5minutes')
        def response = healthCheckService.load('maps.ngdc.noaa.gov')
        println response
        render "success"

    }

    def run(HealthCheck check) {
        if (! check) {
            render status: NOT_FOUND
            return
        }
        def result = healthCheckService.run(check)

        render result
    }

    def report() {
/*
        String findByTagHQL = """
   SELECT HealthCheck
   FROM HealthCheck healthCheck,org.grails.taggable.TagLink tagLink
   WHERE healthCheck.id = tagLink.tagRef
   AND tagLink.type = 'HealthCheck'
   AND tagLink.tag.name IN (:tags)"""
        def results = HealthCheck.executeQuery(findByTagHQL, [tags: params.tags])
*/
        def total = HealthCheck.countByTag(params.tag)
        def success = HealthCheck.findAllByTag(params.tag).findAll {
            it.lastSuccess
        }
        render "found ${success.size()} successful out of ${total} total"
    }
}

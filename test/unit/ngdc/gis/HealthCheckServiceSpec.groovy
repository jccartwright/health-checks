package ngdc.gis

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(HealthCheckService)
class HealthCheckServiceSpec extends Specification {

    def setup() {
        assert service
    }

    def cleanup() {
    }

    void "test ArcGIS Service"() {
        def check = new HealthCheck(url: 'http://maps.ngdc.noaa.gov/arcgis/rest/services/web_mercator/etopo1_hillshade/MapServer')

        when:
        def result = service.run(check)
        println result

        then:
        check.checkCount == 1
        check.lastResponseTime > 0
        check.lastSuccess
    }
}

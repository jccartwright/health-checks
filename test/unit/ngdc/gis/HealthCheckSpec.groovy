package ngdc.gis

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(HealthCheck)
class HealthCheckSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "constuct URL"() {
        def check = new HealthCheck(url:url, pathinfo: pathinfo, queryString: queryString)
        def myUrl = check.constructUrl()

        expect:
        myUrl instanceof java.net.URL
        myUrl.toString() == expected

        where:
        url | pathinfo | queryString | expected
        'http://maps.ngdc.noaa.gov/arcgis/rest/services/web_mercator/etopo1_hillshade/MapServer' | null  |null   | 'http://maps.ngdc.noaa.gov/arcgis/rest/services/web_mercator/etopo1_hillshade/MapServer'
        'http://maps.ngdc.noaa.gov/arcgis/rest/services' | '/web_mercator/etopo1_hillshade/MapServer'  |'f=json'   | 'http://maps.ngdc.noaa.gov/arcgis/rest/services/web_mercator/etopo1_hillshade/MapServer?f=json'
    }
}

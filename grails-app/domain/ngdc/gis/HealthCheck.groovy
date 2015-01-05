package ngdc.gis

import org.grails.taggable.*

class HealthCheck implements Taggable {
    //only required field
    String url
    String pathinfo
    String queryString
    //static hasMany = [queryParams: QueryParam]
    //Double avgResponseTime
    Double lastResponseTime
    String responseChecksum
    //TODO store last response in BLOB?
    byte[] lastResponse
    Integer checkCount = 0
    //null indicates check only run on demand
    String checkInterval
    Date lastUpdated
    Boolean lastSuccess

    static constraints = {
        url nullable: false, url:true
        pathinfo nullable: true, blank: false
        queryString nullable: true, blank: false
        //avgResponseTime nullable: true
        lastResponseTime nullable: true
        responseChecksum nullable: true
        checkCount nullable: false
        checkInterval nullable: true, inList: ['5minutes', '15minutes','hourly','daily','weekly']
        lastSuccess nullable: true
        lastResponse nullable: true, maxSize: 5000000
        //queryParams nullable: true
    }

    def constructUrl() {
        //is this any clearer?
        //"${this.url}${pathinfo ?: ''}${queryString ? '?'+queryString : ''}"

        def url = this.url
        if (pathinfo) {
            url += pathinfo
        }
        if (queryString) {
            url += "?${queryString}"
        }
        return new URL(url)
    }
}

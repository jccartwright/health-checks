import ngdc.gis.HealthCheck

class BootStrap {

    def init = { servletContext ->
        //HealthCheck.findOrSaveWhere(url: 'http://maps.ngdc.noaa.gov/arcgis/rest/services/web_mercator/etopo1_hillshade/MapServer?').setTags(['arcgis','maps1'])
//        HealthCheck.findOrSaveWhere(url: 'http://maps.ngdc.noaa.gov/arcgis/rest/services/web_mercator/etopo1_hillshade/MapServer?').setTags(['arcgis','maps2'])
//        HealthCheck.findOrSaveWhere(url: 'http://maps.ngdc.noaa.gov/arcgis/rest/services/web_mercator/etopo1_hillshade/MapServer?').setTags(['wms','maps1'])
//        HealthCheck.findOrSaveWhere(url: 'http://maps.ngdc.noaa.gov/arcgis/rest/services/web_mercator/etopo1_hillshade/MapServer?').setTags(['wms','maps1'])

        new HealthCheck(url: 'http://maps.ngdc.noaa.gov/arcgis/rest/services/web_mercator/etopo1_hillshade/MapServer?').save(failOnError: true).setTags(['arcgis','maps1'])
        new HealthCheck(url: 'http://maps.ngdc.noaa.gov/arcgis/rest/services/web_mercator/etopo1_hillshade/MapServer?').save(failOnError: true).setTags(['arcgis','maps2'])
        new HealthCheck(url: 'http://maps.ngdc.noaa.gov/arcgis/rest/services/web_mercator/etopo1_hillshade/MapServer?').save(failOnError: true).setTags(['wms','maps1'])
        new HealthCheck(url: 'http://maps.ngdc.noaa.gov/arcgis/rest/services/web_mercator/etopo1_hillshade/MapServer?').save(failOnError: true).setTags(['wms','maps1'])
    }

    def destroy = {
    }
}

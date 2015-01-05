package ngdc.gis

class QueryParam {
    String name
    String value

    static constraints = {
        name blank: false
        value blank: false
    }
}

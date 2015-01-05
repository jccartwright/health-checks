package ngdc.gis



class HourlyJob {
    static triggers = {
        cron name: 'hourlyTrigger', cronExpression: "0 0 * * * ?"
    }

    def execute() {
        println 'executing hourly job...'
    }
}

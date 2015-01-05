package ngdc.gis



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class QueryParamController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond QueryParam.list(params), model:[queryParamInstanceCount: QueryParam.count()]
    }

    def show(QueryParam queryParamInstance) {
        respond queryParamInstance
    }

    def create() {
        respond new QueryParam(params)
    }

    @Transactional
    def save(QueryParam queryParamInstance) {
        if (queryParamInstance == null) {
            notFound()
            return
        }

        if (queryParamInstance.hasErrors()) {
            respond queryParamInstance.errors, view:'create'
            return
        }

        queryParamInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'queryParam.label', default: 'QueryParam'), queryParamInstance.id])
                redirect queryParamInstance
            }
            '*' { respond queryParamInstance, [status: CREATED] }
        }
    }

    def edit(QueryParam queryParamInstance) {
        respond queryParamInstance
    }

    @Transactional
    def update(QueryParam queryParamInstance) {
        if (queryParamInstance == null) {
            notFound()
            return
        }

        if (queryParamInstance.hasErrors()) {
            respond queryParamInstance.errors, view:'edit'
            return
        }

        queryParamInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'QueryParam.label', default: 'QueryParam'), queryParamInstance.id])
                redirect queryParamInstance
            }
            '*'{ respond queryParamInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(QueryParam queryParamInstance) {

        if (queryParamInstance == null) {
            notFound()
            return
        }

        queryParamInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'QueryParam.label', default: 'QueryParam'), queryParamInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'queryParam.label', default: 'QueryParam'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

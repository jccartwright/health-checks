<%@ page import="ngdc.gis.QueryParam" %>



<div class="fieldcontain ${hasErrors(bean: queryParamInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="queryParam.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${queryParamInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: queryParamInstance, field: 'value', 'error')} required">
	<label for="value">
		<g:message code="queryParam.value.label" default="Value" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="value" required="" value="${queryParamInstance?.value}"/>

</div>


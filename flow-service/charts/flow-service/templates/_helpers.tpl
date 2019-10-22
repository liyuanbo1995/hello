{{/* vim: set filetype=mustache: */}}
{{- /*
service.labels.standard prints the standard service Helm labels.
The standard labels are frequently used in metadata.
*/ -}}
{{- define "service.labels.standard" -}}
choerodon.io/release: {{ .Release.Name | quote }}
{{- end -}}
{{- define "service.match.labels" -}}
choerodon.io/release: {{ .Release.Name | quote }}
{{- end -}}
{{- define "service.microservice.labels" -}}
choerodon.io/service: {{ .Chart.Name | quote }}
choerodon.io/version: {{ .Chart.Version | quote }}
{{- end -}}


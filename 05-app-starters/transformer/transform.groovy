import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def jsonSlurper = new JsonSlurper()
def json = jsonSlurper.parseText(new String(payload))

if (json.review.stars == 5) {
    json.review.comment = json.review.comment.toUpperCase()
    println "Accepted and transformed, data to be sent..."
} else {
    println "Data to be sent with no transformation..."
}

return JsonOutput.toJson(json)



#!/usr/bin/env groovy

import com.coremedia.cm.DockerAgent
import com.coremedia.cm.Jenkins

@org.jenkinsci.plugins.workflow.libs.Library(['coremedia-internal', 'coremedia-aws@shared-library-aws-v2']) _

pipeline {

  agent {
    label Jenkins.nodeLabelLarge
  }

  options {
    disableConcurrentBuilds()
    disableResume()
    timestamps()
    timeout(time: 3, unit: 'HOURS')
    buildDiscarder(logRotator(numToKeepStr: '35', artifactNumToKeepStr: '35'))
    durabilityHint('PERFORMANCE_OPTIMIZED')
  }

  stages {
    stage('Build') {
      agent {
        docker {
          image "${Jenkins.ecrPullThroughProxyRegistry}/cm-tools/maven:3.8.6-11.0.17.8-1-cm-1.1.1"
          args DockerAgent.defaultMavenArgs
          reuseNode true
        }
      }
      steps {
        script {
          cmMaven(cmd: "clean install", scanMvnLog: true)
        }
      }
    }
  }

}

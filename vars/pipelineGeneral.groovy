def call(){

    pipeline{

        agent any

        tools{
            nodejs 'NodeJS'
        }
        
        environment{
            projectName = "${env.UrlGitHub}".replaceAll('.+/(.+)\\.git', '$1')toLowerCase()
        } 

       /* triggers{
            pollSCM('* * * * * 1-5')
        } */
        

        stages{

            stage('Fase 2: Construcción de imagen en Docker Desktop') {
                steps {
                    script {
                        def buildimage = new org.devops.lb_buildimagen()
                        buildimage.buildImageDocker("${projectName}")
                    }
                }
            }

            stage('Fase 2: publicar imagen a docker hub.') {
                steps {
                    script {
                        def publicImage = new org.devops.lb_publicardockerhub()
                        publicImage.publicarImage("${projectName}")
                    }
                    
                } 
            }

            stage('Fase 2: Desplegar imagen en docker') {
                steps {
                    script{
                            def deployImg = new org.devops.lb_deploydocker()
                            deployImg.despliegueContenedor("${projectName}")
                        }
                    }    
                }                                        

           stage('Fase 2: analisis con owasp') {
                steps {
                    script{
                        def owasp = new org.devops.lb_owasp()
                        owasp.AnalisisOwasp("${projectName}")
                    }
                }

        }
    

    }
    
  }
}
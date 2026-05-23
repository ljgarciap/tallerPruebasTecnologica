pipeline {
    agent any

    stages {
        stage('1. Clonar Repositorio') {
            steps {
                // Descarga el código actualizado de tu GitHub
                git 'https://github.com/ljgarciap/tallerPruebasTecnologica'
            }
        }

        stage('2. Pruebas Unitarias (JUnit)') {
            steps {
                // Ejecuta SOLO el test unitario aislado del controlador
                sh 'mvn test -Dtest=UsuarioControllerUnitTest'
            }
        }

        stage('3. Pruebas de API (Postman)') {
            steps {
                // Ejecuta la suite de Postman (POST y GET) usando Newman
                // NOTA: Asegúrate de guardar el archivo JSON en la raíz de tu proyecto con este nombre exacto
                sh 'newman run "TECNOLOGICA TALLER_RAA1_U2.postman_collection.json"'
            }
        }

        stage('4. Pruebas de Interfaz (Selenium)') {
            steps {
                // Ejecuta la prueba de UI E2E pasándole el parámetro headless para Jenkins
                sh 'mvn test -Dtest=FormularioSeleniumTest -Dchrome.headless=true'
            }
        }
    }
}
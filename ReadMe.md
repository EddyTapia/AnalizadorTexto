# Analizador de Texto

Primer trabajo para la clase de Análisis y Diseño II.

# Compilación

Desde el directorio raíz de la aplicación:

> javac -d build src/edu/cecar/analizador/Programa.javac

# Generar JAR

Desde el directorio raíz de la aplicación:

> cd build
> jar cvmf ../META-INF/MANIFEST.MF ../dist/analizadorTexto.jar *

# Ejecutar

Desde el directorio raíz de la aplicación:

> cd dist
> java -jar analizadorTexto.jar lista-de-opciones ruta-del-archivo-de-texto
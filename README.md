# MiniTwitter

![enter image description here](https://raw.githubusercontent.com/is-lgarcia/mini_twitter/development/screenshots/logo_minitwitter_blue.png )

**MiniTwitter** es una aplicación creada desde cero simulando la famosa red social de Twitter, teniendo las funcionalidades básicas de la aplicación, como ver los Tweets de los demás usuarios, marcar Tweets favoritos y cambiar la información de perfil colocando una foto personalizada desde la galería.

# Capturas de Pantalla

![Pantalla de Inicio de Sesión](https://raw.githubusercontent.com/is-lgarcia/mini_twitter/development/screenshots/Screenshot_1592931295.png =240x450)  ![Pantalla de Registro](https://raw.githubusercontent.com/is-lgarcia/mini_twitter/development/screenshots/Screenshot_1592931313.png =240x450)  ![Listado de Tweets](https://raw.githubusercontent.com/is-lgarcia/mini_twitter/development/screenshots/Screenshot_1592931578.png =240x450)

![Tweets Favoritos](https://raw.githubusercontent.com/is-lgarcia/mini_twitter/development/screenshots/Screenshot_1592931593.png =240x450)  ![Información de Perfil](https://raw.githubusercontent.com/is-lgarcia/mini_twitter/development/screenshots/Screenshot_1592931602.png =240x450)   

## Librerias usadas:

* **Arquitectura** - Colección de bibliotecas que lo ayudan a diseñar aplicaciones robustas, comprobables y mantenibles. Comience con clases para administrar el ciclo de vida de su componente UI y manejar la persistencia de datos.
	* **LifeCycles** - Cree una interfaz de usuario que responda automáticamente a los eventos del ciclo de vida.
	* **LiveData** - Cree objetos de datos que notifiquen las vistas cuando cambie la base de datos subyacente.
	* **Navigation** - Maneja todo lo necesario para la navegación en la aplicación.
	* **ViewModel** - Almacenar datos relacionados con la interfaz de usuario que no se destruyen en las rotaciones de aplicaciones. Programe fácilmente tareas asincrónicas para una ejecución óptima.
	* **DataBinding** - Declarativamente enlazar datos observables a elementos de la interfaz de usuario.
* **UI** - Detalles de diseño
	* **Material Design** - Componentes de InputLayout para crear una mejor experiencia al usuario.
* **Tercero**
	* **Retrofit** - Un cliente HTTP de tipo seguro para Android, con esto consumimos la Api de la URL: [https://minitwitter.com:3001/api/](https://www.minitwitter.com:3001/api/)
	* **Converter-Gson** - Conversor de respuesta de la API a un formato de tipo Json.
	* **Glide** - para cargar las imágenes.


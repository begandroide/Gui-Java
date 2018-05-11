# GUI Carrito de compras JAVA

# Table of Contents
1. [About .JAR](#AboutJar)
	1. [¿Qué es .JAR?](#WhatIsJar)
	2. [Estructura Compilada](#EstructuraJar)
	3. [Como usarlo](#HowToJar)
2. [Inicializando el proyecto](#introduction)
3. [Lógica de clases](#logic)
4. [Lógica del módulo](#logicModule)
5. [First Steps](#firstSteps)
	1. [Requisitos para listar productos](#listing)
		1. [Catalog ()](#catalog)
	2. [Requisitos para añadir un producto al carro](#addItems)
6. [Sobre los botones](#buttons)
	1. [Interfaz Tienda Virtual](#tiendaVirtual)
	 	1. [Añadir al carro](#addToCarro)
	 	2. [Ir al carro](#goToCarro)
	2. [Interfaz Mi carrito](#miCarrito)
		1. [Modificar cantidad seleccionado](#updateQuantity)
		2. [Ir a Tienda virtual](#goToTienda)
		2. [Finalizar compra](#endPurchase)


# About .JAR <a name="AboutJar"></a>
## ¿Qué es un .JAR? <a name="WhatIsJar"></a>
Es **Java Archive** el cual se comprime en formato .ZIP y extension .JAR. Lo interesante de estos tipos de archivos es que pueden ser aplicaciones Runnables, o tambien pueden ser librerias o módulos, las cuales pueden ser usadas en distintos programas que la importen.

Para esta ocasión, se les dará un archivo de extension .jar el cual tiene compiladas las clases necesarias para gestar la interfaz gráfica y utilizarla en sus proyectos.

## Estructura compilada en el .JAR <a name="EstructuraJar"></a>

sea una estructura de directorio tal como:

```
project    
│
└───src
│   └───com
|	|	└───bgautier
|	|	│   │--->BibliotecaInterface.java
|	|	│   │--->Books.java
|	|	|	|--->CarritoInterface.java
|	|	|	|--->UserInterface.java
│   
└───out
│   └───com
|	│   └───bgautier
|	│   |   │--->BibliotecaInterface.class
|	│   |   │--->Books.class
|	│   |   │--->CarritoInterface.class
|	│   |   │--->UserInterface.class
------------------------------------------
```

En donde el directorio src es dnde se encuentra un paquete **com.bgautier**, en el cual se encuentra el fuente de nuestro programa.

Al compilar los fuentes es necesario hacerlo de forma ordenada y en una carpeta aparte para no
confundirse. Por lo tanto se genera el directorio "out" en el cual se cargan las clases
compiladas.

Para compilar el proyecto anterior es necesario pasar solo las clases .java y se hace asi:
```bash
	javac ./src/com/bgautier/*.java -d out/
```

sigue la regla:
```bash
	javac <all-java-classes-to-compile.java> -d <directory-output>
```

Para crear el archivo .jar es necesario pasarle los archivos compilados, osea los archivos **.class**

Por lo tanto, se usa:
```bash
	jar cfv interface.jar ./out/ .
```

Lo anterior entrega el archivo **interface.jar**; el modulo que utilizaremos en esta instancia.

## How use it? <a name="HowToJar"></a>

Supongamos tenemos un archivo Main.java como el siguiente:
```java
	public class Main{
		 public static void main(String[] args)
		 {
		 	UserInterface mainInterface = new UserInterface();
		 }
	}
```

Hay dos formas para usar un .JAR:

- Si te acomoda usar la linea de comandos, compilamos de esta manera:

```bash
	javac -cp .:interface.jar Main.java
```

- Si usarás un IDE como intellij idea: Es necesario ir a "project structure" modules->dependences->add Jar y seleccionar el .jar entregado.

Lo anterior **no compilará** dado que Main esta usando la clase *UserInterface*
y no la está importando desde la clase principal.

Por lo anterior, es mandatorio importar el .jar.

Si volvemos a la estructura de directorios como ejemplo; debemos utilizar el paquete (*package com.bgautier*) en nuestro main:

Entonces el Main para importar hace lo siguiente:
```java
	import com.bgautier.*;

	public class Main{
		 public static void main(String[] args)
		 {
		 	UserInterface mainInterface = new UserInterface();
		 }
	}
```

#### but, why? :neutral_face: <a name="brieff"></a>

El compilador de Java traduce a bitcode al compilar un archivo *main.java*, esto se refleja que la salida es un archivo *main.class*, el cual contiene todo el binario necesario para JVM.

Ante lo anterior, cuando le pasamos al compilador javac -classpath .:classes.jar Main.java; estamos pasando como argumento nuestro codigo compilado desde :classes.jar, que sabemos está dentro de un paquete "com.bgautier.\*.class". 

Por lo cual, cuando importamos en el main *com.bgautier* estamos importando el bitcode contenido en el .jar.  



## Inicializando el proyecto <a name="introduction"></a>

El modulo entregado debe ser importado mediante **import com.bgautier.**; De esta forma usted podrá usar la interfaz a su gusto. 

Las clases que existen en el modulo son:
- UserInterface
- StoreInterface 
- CarritoInterface
- Product (abstract)
- Logic (abstract)

## Lógica de clases <a name="logic"></a>

Las primeras tres clases son la parte gráfica o front-end de la aplicación.
Para el correcto funcionamiento de la interfaz, es necesario tener productos, por lo que se implementa una clase abstracta para **producto** la cual es así:

```java
abstract public class Product {
    abstract public String getName();
    abstract public float getPrice();
    abstract public int getStock();
    abstract public String getDescription();
    abstract public void setStock(int stock);
    @Override
    public String toString() {
        return getName();
    }
}
```
Tambien internamente, para motivos de la lógica de la interfaz, existe una clase abstracta, llamada Logic, la cual está encargada de toda la lógica o backend 
de la interfaz. Mas [adelante](#logicModule) se muestra la estructura de Logic. 

## Lógica del modulo: <a name="logicModule"></a>

El contexto es una tienda virtual, por lo que es imperativo 


La interfaz gráfica se inicializa mediante la clase UserInterface, se detalla la firma del constructor de la clase:
 
```java
	public UserInterface(Logic logic){
			this.logic=logic;
	}
```

Es requisito tener la clase Logic, ésta es una clase abstracta (deben heredarla, extenderla) la cual se describe a continuación:

```java
abstract public class Logic{
	
	/*
	* function to load products catalog, return type of 
	* function is generics. 
	*@param catalEdited if the catalog was changed, we 
	* can give the new catalog.
	*@return given the internal logic of interface, its    
	* required that return type be iterable by for a      
	* foreach type.
	*/
	public abstract <T> T catalog(Object catalEdited);

	/*
	* function to load caracteristics of user.
	*@param nombre name of user
	*@param local location of user
	*@param edad age of user
	*@return functions returns a value of type Object from
	* java.lang
	*¿requisitos para esta clase?
	*/
	public abstract Object creaUsuario(String nombre, String local, int edad);
	
	/*
	* function to add product item to a Object.
	*@param user, Object where the producto will be added 
	*@param book, product to add to my Carrito
	*@param cant quantity of product to add
	*@return function return a String with the message to show 
	* by console when a product its added.
	*/
	public abstract String addItem(Object user,Product producto,int cant);
	
	/*
	* function to remove product item to a Object.
	*@param user, Object where the product will be removed 
	*@param book, product to remove to my Carrito
	*@param cant quantity of product to remove
	*@return function return a String with the message to show 
	* by console when a product its removed.
	*/
	public abstract String removeItem(Object user, Product producto, int cant);
	
	/*
	* function to load products selected by a user.
	*@param user Object that contains products selected by user
	*@return this function returns a iterable object, of type 
	* Map<String,Integer>, this contain name and number of products
	* of a user.
	*/
	public abstract Map<String,Integer> getProductsSelected(Object user);
	
	/*
	* function to end purchase and return the shipping dispatch.
	*@param no parameters.
	*@return this functions returns a String pre-formated that 
	* contains the shipping dispatch to the products in the Carrito.
	*/
	public abstract String finalizarCompra();
}
```
**Lo entregado no compilará dado que la clase Mylogic no ha sobre-escrito el tipo de valor
de retorno. Recuerde que este debe ser iterable por un for tipo foreach. Se orienta de la siguiente
manera: investigue sobre generics en JAVA. Una vez cambiado el valor de retorno de la función catalog()
recién podrá compilar y ejecutar la interfaz.**

Para compilar y ejecutar se usa **makefile**, las operaciones deben ser:
- make -> compilar
- make run -> ejecutar
- make clean -> limpiar directorio.

Dado lo anterior, para iniciar una instancia del interfaz, como requisito minimo es:
- importar clases del .jar
- Implementar la clase Logic , ejemplo: generamos una clase que se llama Mylogic.java q extiende desde Logic.
- Instanciar UserInterface, con el constructor de la clase, que recibe como parámetro la lógica implementada en el paso anterior.
- Llamar al método modela de la clase instanciada UserInterface. 

Un ejemplo correcto de ejecución:
```java
	import com.bgautier.*;

	public class Main{
		public static void main(String[] args)
		{
			UserInterface gui = new UserInterface(new Mylogic());
			gui.Modela();
		}
	}
```
Nosotros les pasaremos la logica de abajo y el Main de arriba:
```java
	import com.bgautier.*;

	public class Mylogic extends Logic{
		@Override
    	public ArrayList<Object> catalog(Object catalEdited) 
		{
			return null;
		}

		@Override
    	public Object creaUsuario(String nombre, String local, int edad) {
        	return null;
    	}

    	@Override
    	public String addItem(Object user, Product product, int cant)
    	{
    		return null;
    	}
    	
    	@Override
    	public Map<String, Integer> getProductsSelected(Object user) {
        	return null;
    	}

    	@Override
    	public String removeItem(Object user, Product product, int cant) {
	        return null;
    	}

    	@Override
    	public String finalizarCompra() {
	        return null;
    	}
	}
```

Con lo anterior ya debieran poder compilar y ver la pagina de inicio de la interfaz.

## First Steps <a name="firstSteps"></a>

Primero verán una interfaz que pide nombre, localizacion y edad. Al presionar **Comenzar compra**
ingresarán a la interfaz tienda, en donde oportunamente se listaran los productos del catálogo en el panel izquierdo de la pantalla. 

***Achtung***: Al presionar Comenzar Compra, por consola mostrará un mensaje de recomendación para implementar la actual interfaz. 

### Requisitos para listar productos <a name="Listing"></a>

Para listar los productos hace basta cargarlos mediante el método catalogo() de **Mylogic.java**

#### Catalog() <a name="catalog"></a>

Funcion de Mylogic que entrega un objeto que contiene todos los libros. El retorno debe ser iterable en un loop tal como:
```java
	for(Books book: allBooks)
	{
		...
	}
```

### Requisitos para añadir un producto al carro <a name="addItems"></a>

Funcion AddItem(...) de Mylogic, pide como parámetros **user, product, cant**. La idea principal es que al carrito de un usuario podamos añadirle productos, indicandole cual producto es y la cantidad a añadir.
  
## Sobre los botones <a name="buttons"></a>

### Interfaz Tienda Virtual <a name="tiendaVirtual"></a>

#### Añadir al carro <a name="addToCarro"></a>

Si ya tienes productos listados, puedes presionarlo, debes recibir las caracteristicas principales de este (nombre,precio,descripcion,stock). Luego presionar Añadir al carro, éste llama a la funcion addItem(...) de Mylogic y debe hacer lo suyo...

#### Ir al carro <a name="goToCarro"></a>

Al presionarlo por primera vez, explotará un mensaje diciendo las clases necesarias para cargar de forma correcta los productos solicitados por un usuario.

### Interfaz Mi carrito <a name="miCarrito"></a>

#### Modificar cantidad seleccionado <a name="updateQuantity"></a>

Botón que pregunta cuantos productos del seleccionado deseas remover. Es necesaria la función removeItem(...) de Logic.

#### Ir a Tienda virtual <a name="goToTienda"></a>

Te redirige hacia la vista Tienda Virtual desde el carrito.

#### Finalizar compra <a name="endPurchase"></a>

Boton para enviar petición de finalizar compra. Ésta debe estar implementada en tu logica, y debes recibir una orden de despacho para tu usuario.

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp3_s8_giovany_oliva;

/**
 *
 * @author k-9_o
 */

import java.util.*;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;


public class Exp3_S8_Giovany_Oliva {

    /**
     * @param args the command line arguments
     */
    
    
        // Arreglos para ventas, asientos y clientes
    private static final  Venta[] ventas = new Venta[100]; // Tamaño inicial del arreglo
    private static final int[] asientos = new int[50]; // 50 asientos en el teatro
    private static final ArrayList<Cliente> clientes = new ArrayList<>();
    private static final AtomicInteger contadorClienteID = new AtomicInteger(1);  // Contador atómico de ID Cliente
        private static final AtomicInteger contadorVentasID = new AtomicInteger(1); // Contador atómico de ID Ventas

    // Lista para descuentos
    private static final List<Descuento> descuentos = new ArrayList<>();

    // Funciones auxiliares para manejo de fechas
    private static boolean isFechaDentroRango(LocalDate fecha, LocalDate fechaInicio, LocalDate fechaFin) {
        return fecha.isAfter(fechaInicio.minusDays(1)) && fecha.isBefore(fechaFin.plusDays(1));
    }

    // Función para obtener el descuento aplicable
    private static Descuento getDescuentoAplicable(String tipoCliente, LocalDate fechaActual) {
        for (Descuento descuento : descuentos) {
            if (descuento.getTipoCliente().equals(tipoCliente) && isFechaDentroRango(fechaActual, descuento.getFechasVigencia()[0], descuento.getFechasVigencia()[1])) {
                return descuento;
            }
        }
        return null;
    }

    // Función para inicializar los arreglos de asientos y clientes
    private static void iniciarAsientosYClientes() {
        // Inicializar asientos como disponibles (valor 0)
        for (int i = 0; i < asientos.length; i++) {
            asientos[i] = 0;
        }

    }

    // Función para inicializar los descuentos
    private static void iniciarDescuentos() {
        descuentos.add(new Descuento("Estudiante", 10, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 31)));
        descuentos.add(new Descuento("Tercera Edad", 15, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 12, 31)));
    }
    
    private static class Cliente {
        private final int id;
        private String nombre;
        private String tipoCliente;

        public Cliente(int id, String nombre, String tipoCliente) {
            this.id = id;
            this.nombre = nombre;
            this.tipoCliente = tipoCliente;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getTipo() {
            return tipoCliente;
        }

        public void setTipoCliente(String tipoCliente) {
            this.tipoCliente = tipoCliente;
        }
    }

    
    // Clase interna para representar una venta
    private static class Venta {
        private int idVenta;
        private String tipoEntrada;
        private int numeroAsiento;
        private double precioTotal;
        private Map<String, Object> datosCliente;

        public Venta(int idVenta, String tipoEntrada, int numeroAsiento, double precioTotal, 
             Map<String, Object> datosCliente, double descuentoAplicado) {
            this.idVenta = idVenta;
            this.tipoEntrada = tipoEntrada;
            this.numeroAsiento = numeroAsiento;
            this.precioTotal = precioTotal;
            this.datosCliente = datosCliente;
        }

        // Getters
        public int getIdVenta() {
            return idVenta;
        }

        public String getTipoEntrada() {
            return tipoEntrada;
        }

        public int getNumeroAsiento() {
            return numeroAsiento;
        }

        public double getPrecioTotal() {
            return precioTotal;
        }

        public Map<String, Object> getDatosCliente() {
            return datosCliente;
        }

        // Setters
        public void setIdVenta(int idVenta) {
            this.idVenta = idVenta;
        }

        public void setTipoEntrada(String tipoEntrada) {
            this.tipoEntrada = tipoEntrada;
        }

        public void setNumeroAsiento(int numeroAsiento) {
            this.numeroAsiento = numeroAsiento;
        }

        public void setPrecioTotal(double precioTotal) {
            this.precioTotal = precioTotal;
        }

        public void setDatosCliente(Map<String, Object> datosCliente) {
            this.datosCliente = datosCliente;
        }
    }


    // Clase interna para representar un descuento
    private static class Descuento {
        private String tipoCliente;
        private double porcentajeDescuento;
        private LocalDate[] fechasVigencia;

        public Descuento(String tipoCliente, double porcentajeDescuento, LocalDate fechaInicio, LocalDate fechaFin) {
            this.tipoCliente = tipoCliente;
            this.porcentajeDescuento = porcentajeDescuento;
            this.fechasVigencia = new LocalDate[]{fechaInicio, fechaFin};
        }

        // Getters
        public String getTipoCliente() {
            return tipoCliente.toLowerCase();
        }

        public double getPorcentajeDescuento() {
            return porcentajeDescuento;
        }

        public LocalDate[] getFechasVigencia() {
            return fechasVigencia;
        }

        // Setters
        public void setTipoCliente(String tipoCliente) {
            this.tipoCliente = tipoCliente;
        }

        public void setPorcentajeDescuento(double porcentajeDescuento) {
            this.porcentajeDescuento = porcentajeDescuento;
        }

        public void setFechasVigencia(LocalDate[] fechasVigencia) {
            this.fechasVigencia = fechasVigencia;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicializar arreglos y listas (ya realizado en pasos anteriores)
        iniciarAsientosYClientes();
        iniciarDescuentos();

        while (true) {
            // Menú principal
            System.out.println("\n\nSistema de Venta de Entradas del Teatro");
            System.out.println("-----------------------------------------");
            System.out.println("1. Vender entrada");
            System.out.println("2. Imprimir boleta");
            System.out.println("3. Consultar estado de asiento");
            System.out.println("4. Ver descuentos disponibles");
            System.out.println("5. Ver, Editar o Eliminar clientes");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    venderEntrada(scanner);
                    break;
                case 2:
                    imprimirBoleta(scanner);
                    break;
                case 3:
                    consultarEstadoAsiento(scanner);
                    break;
                case 4:
                    verDescuentosDisponibles();
                    break;
                case 5:
                    editarOEliminarCliente(scanner);
                    break;
                case 6:
                    System.out.println("Saliendo del sistema...");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
            }
        }
    }
    
    
    
    private static void editarOEliminarCliente(Scanner scanner){
        System.out.println("\n\nVer, Editar o Eliminar un Cliente");
        System.out.println("1. Mostrar Clientes");
        System.out.println("2. Editar Cliente");
        System.out.println("3. Eliminar Cliente");
        System.out.println("4. Salir");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        
        switch(opcion){
            case 1 :
                mostrarClientes();
                break;
            case 2 :
                editarCliente(scanner);
                break;
            case 3 :
                eliminarCliente(scanner);
                break;
            case 4 :
                System.out.println("Volviendo al menú principal");
                return;
            default:
                System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
        }
        
    }
    
    private static void mostrarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("\nNo hay clientes registrados.");
            return;
        }

        System.out.println("\n\nLista de Clientes:");
        for (Cliente cliente : clientes) {
            System.out.println("-----------------------------------------");
            System.out.println("ID: " + cliente.getId());
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Tipo: " + cliente.getTipo());
            System.out.println("-----------------------------------------");
        }
    }
    
    private static void editarCliente(Scanner scanner) {
        System.out.print("Ingrese nombre del cliente: ");
        String nombreCliente = scanner.nextLine();

        System.out.print("Ingrese tipo de cliente (estudiante/general/tercera edad): ");
        String tipoCliente = scanner.nextLine();

        // Validate customer information
        int idCliente = buscarIDCliente(nombreCliente, tipoCliente);
        if (idCliente == -1){
            System.out.println("\n\nCliente no encontrado");
            return;
        }
        
        
        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("\nCliente no encontrado con el ID proporcionado.");
            return;
        }

        // Display current customer information
        System.out.println("\n\nInformación actual del cliente:");
        System.out.println("ID: " + cliente.getId());
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("Tipo: " + cliente.getTipo());

        // Prompt for updated information
        System.out.print("\nIngrese el nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Ingrese el nuevo tipo (estudiante/general/tercera edad): ");
        String nuevoTipoCliente = scanner.nextLine();

        // Update customer information in the array
        cliente.setNombre(nuevoNombre);
        cliente.setTipoCliente(nuevoTipoCliente);
        System.out.println("\nInformación del cliente actualizada exitosamente.");
    }
    
    
    private static void eliminarCliente(Scanner scanner) {
        System.out.print("Ingrese nombre del cliente: ");
        String nombreCliente = scanner.nextLine();

        System.out.print("Ingrese tipo de cliente (estudiante/general/tercera edad): ");
        String tipoCliente = scanner.nextLine();

        // Validate customer information
        int idCliente = buscarIDCliente(nombreCliente, tipoCliente);
        if (idCliente == -1){
            System.out.println("\n\nCliente no encontrado");
            return;
        }
        if (idCliente != -1) {
            clientes.remove(idCliente - 1);
            System.out.println("\nCliente eliminado exitosamente.");
        } else {
            System.out.println("\nCliente no encontrado con el ID proporcionado.");
        }
    }
        
    private static void imprimirBoleta(Scanner scanner) {
        System.out.println("\n\nImprimir boleta");

        // 1. Solicitar ID de venta
        System.out.print("Ingrese ID de venta: ");
        int idVenta = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        // 2. Buscar la venta por ID
        Venta venta = buscarVentaPorId(idVenta);
        if (venta == null) {
            System.out.println("Venta no encontrada. Intente nuevamente.");
            return;
        }

        // 3. Imprimir información de la venta
        System.out.println("\n\n**Boleta de Venta**");
        System.out.println("-------------------------");
        System.out.println("ID de Venta: " + venta.getIdVenta());
        System.out.println("Tipo de Entrada: " + venta.getTipoEntrada());
        System.out.println("Número de Asiento: " + venta.getNumeroAsiento());
        System.out.println("Precio Total: $" + venta.getPrecioTotal());
        System.out.println("Datos del Cliente:");
        System.out.println("  - ID: " + venta.getDatosCliente().get("idCliente"));
        System.out.println("  - Nombre: " + venta.getDatosCliente().get("nombreCliente"));
        System.out.println("  - Tipo de Cliente: " + venta.getDatosCliente().get("tipoCliente"));
        System.out.println("-------------------------");
    }
    
    private static void venderEntrada(Scanner scanner) {
        System.out.println("\n\nVenta de entrada");

        // 1. Collect customer information
        System.out.print("Ingrese nombre del cliente: ");
        String nombreCliente = scanner.nextLine();

        System.out.print("Ingrese tipo de cliente (estudiante/general/tercera edad): ");
        String tipoCliente = scanner.nextLine().toLowerCase();

        // Validate customer information
        int idCliente = buscarIDCliente(nombreCliente, tipoCliente);
        if (idCliente == -1) {
            // Register new customer if needed
            System.out.println("Cliente no registrado. ¿Desea registrar un nuevo cliente? (Si/No)");
            String respuesta = scanner.nextLine().toLowerCase();

            if (respuesta.equalsIgnoreCase("si")) {
                idCliente = contadorClienteID.getAndIncrement();
                registrarCliente(idCliente, nombreCliente, tipoCliente);
                System.out.println("Cliente registrado exitosamente.");
            } else {
                System.out.println("Cancelando venta de entrada.");
                return;
            }
        }

        // 2. Select a seat
        mostrarAsientosDisponibles();
        System.out.print("Ingrese número de asiento: ");
        int numeroAsiento = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Validate seat number
        if (numeroAsiento < 1 || numeroAsiento > asientos.length) {
            System.out.println("Número de asiento no válido. Intente nuevamente.");
            return;
        }

        if (asientos[numeroAsiento - 1] != 0) {
            System.out.println("El asiento seleccionado no está disponible. Intente nuevamente.");
            return;
        }

        // 3. Aplicar descuentos y mostrar precio
        double precioOriginal = obtenerPrecioEntrada(); // Calculate base price
        Descuento descuentoAplicable = getDescuentoAplicable(tipoCliente, LocalDate.now()); // Retrieve applicable discount
        double precioDescuento = aplicarDescuento(precioOriginal, descuentoAplicable); // Apply discount

        // Display purchase details
        System.out.println("\n\n**Detalle de Compra**");
        System.out.println("-------------------------");
        System.out.printf("Precio Original: $%.2f\n", precioOriginal);
        System.out.println("Descuento Aplicado: " + (descuentoAplicable != null ? descuentoAplicable.getPorcentajeDescuento() + "%" : "0%"));
        System.out.printf("Precio a Pagar: $%.2f\n", precioDescuento);
        System.out.println("-------------------------");
        
        
        // Solicitar confirmación de compra
        System.out.print("\n¿Desea continuar con la compra? (Si/No): ");
        String respuesta = scanner.nextLine().toLowerCase();

        // Procesar la compra o cancelar la operación
        if (respuesta.equalsIgnoreCase("si")) {
            int idVenta = obtenerIDIncrementalVenta();
            Venta venta = new Venta(idVenta, tipoCliente, numeroAsiento, precioDescuento, crearDatosCliente(idCliente, nombreCliente, tipoCliente), descuentoAplicable != null ? descuentoAplicable.getPorcentajeDescuento() : 0);
            ventas[idVenta - 1] = venta;
            asientos[numeroAsiento - 1] = idVenta;

            // Mostrar boleta
            System.out.println("\n\n**Boleta de Venta**");
            System.out.println("-------------------------");
            System.out.println("ID de Venta: " + venta.getIdVenta());
            System.out.println("Tipo de Entrada: " + venta.getTipoEntrada());
            System.out.println("Número de Asiento: " + venta.getNumeroAsiento());
            System.out.println("Precio Original: $" + precioOriginal);
            System.out.println("Descuento Aplicado: " + (descuentoAplicable != null ? descuentoAplicable.getPorcentajeDescuento() + "%" : "0%"));
            System.out.println("Precio Total: $" + venta.getPrecioTotal());
            System.out.println("Datos del Cliente:");
            for (Map.Entry<String, Object> entry : venta.getDatosCliente().entrySet()) {
                System.out.println(" - " + entry.getKey() + ": " + entry.getValue());
            }
            System.out.println("-------------------------");
            System.out.println("\nCompra realizada exitosamente!");
    
        } else {
          System.out.println("\nCompra cancelada.");
        }

    }

    // Function to create a Map of customer data
    private static Map<String, Object> crearDatosCliente(int idCliente, String nombreCliente, String tipoCliente) {
        Map<String, Object> datosCliente = new HashMap<>();
        datosCliente.put("idCliente", idCliente);
        datosCliente.put("nombreCliente", nombreCliente);
        datosCliente.put("tipoCliente", tipoCliente);
        return datosCliente;
    }

    // Function to search for a customer by name and type
    private static int buscarIDCliente(String nombreCliente, String tipoCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.getNombre().equals(nombreCliente) && cliente.getTipo().equals(tipoCliente)) {
                return cliente.getId();
            }
        }
        return -1;
    }
    

    // Function to register a new customer
    private static void registrarCliente(int idCliente, String nombreCliente, String tipoCliente) {
        clientes.add(new Cliente(idCliente, nombreCliente, tipoCliente));
    }

    
    // Función auxiliar para buscar una venta por ID
    private static Venta buscarVentaPorId(int idVenta) {
        for (Venta venta : ventas) {
            if (venta != null && venta.getIdVenta() == idVenta) {
                return venta;
            }
        }
        return null;
    }
    
        // Función auxiliar para buscar una venta por ID
    private static Cliente buscarClientePorId(int idCliente) {
        for (Cliente cliente : clientes) {
            if (cliente != null && cliente.getId() == idCliente) {
                return cliente;
            }
        }
        return null;
    }
    

    
    private static void mostrarAsientosDisponibles() {
        System.out.println("\nAsientos Disponibles:");
        System.out.println("--------------------");
        for (int i = 0; i < asientos.length; i++) {
          if (asientos[i] == 0) {
            System.out.println("  - Asiento " + (i + 1));
          }
        }
        System.out.println("--------------------");
    }
    
    private static void consultarEstadoAsiento(Scanner scanner) {
        System.out.println("\n\nEstado de un asiento");

        // 1. Solicitar número de asiento
        System.out.print("Ingrese número de asiento: ");
        int numeroAsiento = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        // 2. Validar número de asiento
        if (numeroAsiento < 1 || numeroAsiento > asientos.length) {
            System.out.println("Número de asiento no válido. Intente nuevamente.");
            return;
        }

        // 3. Verificar estado del asiento
        int estadoAsiento = asientos[numeroAsiento - 1];
        if (estadoAsiento == 0) {
            System.out.println("El asiento " + numeroAsiento + " está disponible.");
        } else {
            System.out.println("El asiento " + numeroAsiento + " está ocupado por la venta " + estadoAsiento);
        }
    }
    

    
    private static void verDescuentosDisponibles() {
        System.out.println("\n\nFunción para mostrar descuentos disponibles");

        if (descuentos.isEmpty()) {
            System.out.println("No hay descuentos disponibles en este momento.");
            return;
        }

        System.out.println("\n**Descuentos Disponibles**");
        System.out.println("-------------------------");
        for (Descuento descuento : descuentos) {
            System.out.println("Tipo de Cliente: " + descuento.getTipoCliente());
            System.out.println("Porcentaje Descuento: " + descuento.getPorcentajeDescuento() + "%");
            System.out.println("Fechas de Vigencia: " + descuento.getFechasVigencia()[0].toString() + " - " + descuento.getFechasVigencia()[1].toString());
            System.out.println("-------------------------");
        }
    }
    
    private static double obtenerPrecioEntrada() {
        double precioBase = 10000.0;
        return precioBase;
    }
    
    private static double aplicarDescuento(double precioOriginal, Descuento descuentoAplicable) {
        if (descuentoAplicable != null) {
            return precioOriginal * (1 - descuentoAplicable.getPorcentajeDescuento() / 100.0);
        } else {
            return precioOriginal; 
        }
    }

    private static int obtenerIDIncrementalVenta() {
      return contadorVentasID.getAndIncrement();
    }
}
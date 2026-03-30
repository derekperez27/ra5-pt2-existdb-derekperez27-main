package es.ilerna.M0486.ra5.pt2.main;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class Main {
	private static final Scanner scanner = new Scanner(System.in);
	
	
	public static void main(String[] args) {
		testConnection();

		int opcio;
		
		do {
			showMenu();
			opcio = llegirOpcio();
			
			switch (opcio) {
				case 1:
					testConnection();
					break;
				case 2:
						System.out.print("Introdueix el nom de la nova subcol·lecció: ");
						String collection_name = scanner.nextLine().trim();
						new_subcollection(collection_name);
					break;
					
				case 3:
						System.out.print("Introdueix el nom de la subcol·lecció a eliminar: ");
						String collection_name_drop = scanner.nextLine().trim();
						drop_subcollection(collection_name_drop);
					break;
					
				case 4:
						System.out.print("Introdueix el nom de la subcol·lecció: ");
						String subcollection_name = scanner.nextLine().trim();
					
						System.out.print("Introdueix la ruta del fitxer XML: ");
						String xml_file = scanner.nextLine().trim();
					
						add_document(subcollection_name, xml_file);
					
					break;
				
				case 5:
						System.out.print("Introdueix el nom de la subcol·lecció: ");
						String subcollection_name_drop = scanner.nextLine().trim();
					
						System.out.print("Introdueix el nom del fitxer XML: ");
						String file_name = scanner.nextLine().trim();
					
						drop_document(subcollection_name_drop, file_name);
					break;
				case 0:
					System.out.println("Programa finalitzat.");
					break;
				default:
					System.out.println("Opció no vàlida.");
			}	
		} while (opcio != 0);
		scanner.close();
	}

	public static void showMenu() {
		System.out.println("===== MENÚ =====");
		System.out.println("1. Provar connexió");
		System.out.println("2. CREAR NOVA SUBCOL·LECCIÓ");
		System.out.println("3. ELIMINAR UNA SUBCOL·LECCIÓ");
		System.out.println("4. AFEGIR UN DOCUMENT XML A UNA SUBCOL·LECCIÓ");
		System.out.println("5. ELIMINAR UN DOCUMENT XML D'UNA SUBCOL·LECCIÓ");
		System.out.println("0. Sortir\n");
		System.out.print("Escull una opció: ");
	}

	public static int llegirOpcio() {
		try {
			int opcio = Integer.parseInt(scanner.nextLine().trim());
			return opcio;
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public static void testConnection() {
		Collection spotydam = null;
		Collection data = null;
		
		try {
			spotydam = ExistDbManager.connect();
			
			if (spotydam == null) {
				System.out.println("No s'ha pogut obrir SPOTYDAM.");
				return;
			}
		
			data = spotydam.getChildCollection("DATA");
		
			if (data == null) {
				System.out.println("No s'ha pogut obrir DATA.");
				return;
			}
		
			System.out.println("Connexió OK a DATA");
			System.out.println("Nom: " + data.getName());
		
			String[] resources = data.listResources();
		
			System.out.println("Documents dins de DATA:");
			if (resources == null || resources.length == 0) {
				System.out.println("No hi ha documents.");
			} else {
				for (String resource : resources) {
					System.out.println("- " + resource);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (data != null) data.close();
				if (spotydam != null) spotydam.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void new_subcollection(String name_of_new_collection) {
		Collection spotydam = null;
		Collection existent = null;
		try {
			spotydam = ExistDbManager.connect();
			if (spotydam == null) {
				System.out.println("No s'ha pogut obrir SPOTYDAM.");
				return;
			}

			existent = spotydam.getChildCollection(name_of_new_collection);
			if (existent != null) {
				System.out.println("La subcol·lecció ja existeix: " + name_of_new_collection);
				return;
			}

			CollectionManagementService cms = (CollectionManagementService) spotydam.getService("CollectionManagementService", "1.0");
			cms.createCollection(name_of_new_collection);
			System.out.println("Subcol·lecció creada correctament: " + name_of_new_collection);
		} catch (Exception e) {
			System.out.println("Error en crear la subcol·lecció: " + e.getMessage());
		} finally {
			try {
				if (existent != null) existent.close();
				if (spotydam != null) spotydam.close();
			} catch (Exception e) {
				System.out.println("Error tancant col·leccions: " + e.getMessage());
			}
		}
	}

	public static void drop_subcollection(String collection_name) {
		Collection spotydam = null;
		Collection subcollection = null;
		try {
			spotydam = ExistDbManager.connect();
			if (spotydam == null) {
				System.out.println("No s'ha pogut obrir SPOTYDAM.");
				return;
			}

			subcollection = spotydam.getChildCollection(collection_name);
			if (subcollection == null) {
				System.out.println("Error controlat: la subcol·lecció no existeix: " + collection_name);
				return;
			}

			CollectionManagementService cms = (CollectionManagementService) spotydam.getService("CollectionManagementService", "1.0");
			cms.removeCollection(collection_name);
			System.out.println("Subcol·lecció eliminada correctament: " + collection_name);
		} catch (Exception e) {
			System.out.println("Error en eliminar la subcol·lecció: " + e.getMessage());
		} finally {
			try {
				if (subcollection != null) subcollection.close();
				if (spotydam != null) spotydam.close();
			} catch (Exception e) {
				System.out.println("Error tancant col·leccions: " + e.getMessage());
			}
		}
	}

	public static void add_document(String subcollection_name, String xml_file) {
		Collection spotydam = null;
		Collection subcollection = null;
		try {
			Path xmlPath = Path.of(xml_file);
			if (!Files.exists(xmlPath) || !Files.isRegularFile(xmlPath)) {
				System.out.println("Error controlat: no existeix el fitxer XML a la ruta indicada.");
				return;
			}

			spotydam = ExistDbManager.connect();
			if (spotydam == null) {
				System.out.println("No s'ha pogut obrir SPOTYDAM.");
				return;
			}

			subcollection = spotydam.getChildCollection(subcollection_name);
			if (subcollection == null) {
				System.out.println("Error controlat: la subcol·lecció no existeix: " + subcollection_name);
				return;
			}

			String fileName = xmlPath.getFileName().toString();
			String xmlContent = Files.readString(xmlPath);

			XMLResource resource = (XMLResource) subcollection.createResource(fileName, "XMLResource");
			resource.setContent(xmlContent);
			subcollection.storeResource(resource);

			System.out.println("Document afegit correctament: " + fileName);
		} catch (Exception e) {
			System.out.println("Error en afegir document XML: " + e.getMessage());
		} finally {
			try {
				if (subcollection != null) subcollection.close();
				if (spotydam != null) spotydam.close();
			} catch (Exception e) {
				System.out.println("Error tancant col·leccions: " + e.getMessage());
			}
		}
	}

	public static void drop_document(String subcollection_name, String file_name) {
		Collection spotydam = null;
		Collection subcollection = null;
		try {
			spotydam = ExistDbManager.connect();
			if (spotydam == null) {
				System.out.println("No s'ha pogut obrir SPOTYDAM.");
				return;
			}

			subcollection = spotydam.getChildCollection(subcollection_name);
			if (subcollection == null) {
				System.out.println("Error controlat: la subcol·lecció no existeix: " + subcollection_name);
				return;
			}

			Resource resource = subcollection.getResource(file_name);
			if (resource == null) {
				System.out.println("Error controlat: el document no existeix: " + file_name);
				return;
			}

			subcollection.removeResource(resource);
			System.out.println("Document eliminat correctament: " + file_name);
		} catch (Exception e) {
			System.out.println("Error en eliminar document XML: " + e.getMessage());
		} finally {
			try {
				if (subcollection != null) subcollection.close();
				if (spotydam != null) spotydam.close();
			} catch (Exception e) {
				System.out.println("Error tancant col·leccions: " + e.getMessage());
			}
		}
	}
}
package com.alkemy.ong.messages;

public class DocumentationMessages {

  //CATEGORY
  public static final String CATEGORY_CONTROLLER = "Category Controller";

  public static final String CATEGORY_CONTROLLER_DESCRIPTION = "Contains the "
    + "endpoints of category entity";

  public static final String CATEGORY_CONTROLLER_SUMMARY_CREATE = "Create Category";

  public static final String CATEGORY_CONTROLLER_SUMMARY_LIST = "List of Categories";

  public static final String CATEGORY_CONTROLLER_SUMMARY_UPDATE = "Update Category";

  public static final String CATEGORY_CONTROLLER_SUMMARY_DELETE = "Delete Category";

  public static final String CATEGORY_CONTROLLER_RESPONSE_200_DESCRIPTION = "Action carried "
    + "out successfully";

  public static final String CATEGORY_CONTROLLER_RESPONSE_201_DESCRIPTION = "Category created";

  public static final String CATEGORY_CONTROLLER_RESPONSE_204_DESCRIPTION = "Category deleted";

  public static final String CATEGORY_CONTROLLER_RESPONSE_400_DESCRIPTION = "Bad Request";

  public static final String CATEGORY_CONTROLLER_RESPONSE_403_DESCRIPTION = "Forbidden, "
    + "you can not access";

  public static final String CATEGORY_CONTROLLER_RESPONSE_404_DESCRIPTION = "Category not found";

  //TESTIMONIAL
  public static final String TESTIMONIAL_CONTROLLER = "Testimonial controller";

  public static final String TESTIMONIAL_CONTROLLER_DESCRIPTION = "Contains the endpoints of testimonial entity";

  public static final String TESTIMONIAL_CONTROLLER_SUMMARY_CREATE = "Create testimonial";

  public static final String TESTIMONIAL_CONTROLLER_SUMMARY_LIST = "List of Categories pageds";

  public static final String TESTIMONIAL_CONTROLLER_SUMMARY_DELETE = "Delete testimonial";

  public static final String TESTIMONIAL_CONTROLLER_SUMMARY_UPDATE = "Update testimonial";

  public static final String TESTIMONIAL_CONTROLLER_RESPONSE_201_DESCRIPTION = "Testimonial created";

  public static final String TESTIMONIAL_CONTROLLER_RESPONSE_403_DESCRIPTION = "Forbidden, you can not access";

  public static final String TESTIMONIAL_CONTROLLER_RESPONSE_400_DESCRIPTION = "Bad Request";

  public static final String TESTIMONIAL_CONTROLLER_RESPONSE_200_DESCRIPTION = "Action carried out successfully";

  public static final String TESTIMONIAL_CONTROLLER_RESPONSE_204_DESCRIPTION = "Testimonial deleted";

  public static final String TESTIMONIAL_CONTROLLER_RESPONSE_404_DESCRIPTION = "Testimonial not found";

  //USER
  public static final String USER_CONTROLLER = "User Controller";

  public static final String USER_CONTROLLER_DESCRIPTION = "Contains the endpoint of User";

  public static final String USER_CONTROLLER_RESPONSE_200_DESCRIPTION = "Action carried out successfully";

  public static final String USER_CONTROLLER_RESPONSE_400_DESCRIPTION = "Bad Request";

  public static final String USER_CONTROLLER_RESPONSE_403_DESCRIPTION = "Action forbidden";

  public static final String AUTHENTICATION_CONTROLLER = "Authentication Controller";

  public static final String AUTHENTICATION_CONTROLLER_DESCRIPTION = "Contains the endpoint of Authentication";

  public static final String AUTHENTICATION_CONTROLLER_LOGIN = "User Login";

  public static final String AUTHENTICATION_CONTROLLER_RESPONSE_200_DESCRIPTION = "Action carried out successfully";

  public static final String AUTHENTICATION_CONTROLLER_RESPONSE_400_DESCRIPTION = "Bad Request";

  public static final String AUTHENTICATION_CONTROLLER_RESPONSE_401_DESCRIPTION = "Bad Credentials";

  public static final String AUTHENTICATION_CONTROLLER_RESPONSE_404_DESCRIPTION = "User not found";

  public static final String AUTHENTICATION_CONTROLLER_RESPONSE_500_DESCRIPTION = "Internal Server Error";

  //MEMBER
  public static final String MEMBER_CONTROLLER = "Member Controller";

  public static final String MEMBER_CONTROLLER_DESCRIPTION = "Contains the "
    + "endpoints of member entity";

  public static final String MEMBER_CONTROLLER_SUMMARY_LIST = "List of Members";

  public static final String MEMBER_CONTROLLER_SUMMARY_CREATE = "Create Member";

  public static final String MEMBER_CONTROLLER_SUMMARY_DELETE = "Delete Member";

  public static final String MEMBER_CONTROLLER_SUMMARY_UPDATE = "Update Member";

  public static final String MEMBER_CONTROLLER_RESPONSE_200_DESCRIPTION = "Action carried "
    + "out successfully";

  public static final String MEMBER_CONTROLLER_RESPONSE_201_DESCRIPTION = "Member created";

  public static final String MEMBER_CONTROLLER_RESPONSE_204_DESCRIPTION = "Member deleted";

  public static final String MEMBER_CONTROLLER_RESPONSE_400_DESCRIPTION = "Bad Request";

  public static final String MEMBER_CONTROLLER_RESPONSE_403_DESCRIPTION = "Forbidden, "
    + "you can not access";

  public static final String MEMBER_CONTROLLER_RESPONSE_404_DESCRIPTION = "Member not found";
    
  public static final String MEMBER_CONTROLLER_RESPONSE_409_DESCRIPTION = "Internal conflict.";
 
  //NEWS

  public static final String NEWS_CONTROLLER = "News Controller";

  public static final String NEWS_CONTROLLER_DESCRIPTION = "Contains the "
    + "endpoints of news entity";

  public static final String NEWS_CONTROLLER_SUMMARY_CREATE = "Create News";

  public static final String NEWS_CONTROLLER_SUMMARY_CREATE_DESCRIPTION = "" +
    "Valida la existencia de los campos enviados, y almacena el registro en la tabla News.";

  public static final String NEWS_CONTROLLER_SUMMARY_LIST = "List of News";

  public static final String NEWS_CONTROLLER_SUMMARY_UPDATE = "Update News";

  public static final String NEWS_CONTROLLER_SUMMARY_UPDATE_DESCRIPTION = "" +
    "Valida que la novedad exista en base al id enviado por parámetro." +
    " En el caso de que no exista devuelve un error, caso contrario actualiza la " +
    "novedad y devolve la misma con los datos actualizados.";

  public static final String NEWS_CONTROLLER_SUMMARY_DELETE = "Delete News";

  public static final String NEWS_CONTROLLER_SUMMARY_DELETE_DESCRIPTION = "" +
    "Valida la existencia en base al id enviado por parámetro. En el caso de que exista, se elimina.";

  public static final String NEWS_CONTROLLER_SUMMARY_FINDBYID = "Get news by ID";
  public static final String NEWS_CONTROLLER_SUMMARY_FINDBYID_DESCRIPTION = "" +
    "Muestra todos los campos de una New en base al id enviado por parámetro.";

  public static final String NEWS_CONTROLLER_SUMMARY_PAGINATION = "List news per page";
  public static final String NEWS_CONTROLLER_SUMMARY_PAGINATION_DESCRIPTION = "" +
    "Cuando se realice esta petición para listar, los resultados se paginan de a 10." +
    " Se mostra los resultados y las urls para la página anterior y siguiente (si corresponden)." +
    " La página actual se obtiene del parámetro de query ?page.";

  public static final String NEWS_CONTROLLER_RESPONSE_200_DESCRIPTION = "Action carried "
    + "out successfully";

  public static final String NEWS_CONTROLLER_RESPONSE_201_DESCRIPTION = "News created";

  public static final String NEWS_CONTROLLER_RESPONSE_204_DESCRIPTION = "News deleted ";

  public static final String NEWS_CONTROLLER_RESPONSE_400_DESCRIPTION = "Bad Request ";

  public static final String NEWS_CONTROLLER_RESPONSE_403_DESCRIPTION = "Forbidden ";

  public static final String NEWS_CONTROLLER_RESPONSE_404_DESCRIPTION = "Not Found"
    + "you can not access";
}

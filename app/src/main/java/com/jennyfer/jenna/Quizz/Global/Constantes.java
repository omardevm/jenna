package com.jennyfer.jenna.Quizz.Global;

public class Constantes {
    private static String BASE_URL = "https://pitav3.000webhostapp.com/_quizz/";
    public static String GET_PREGUNTAS = BASE_URL+"preguntas/select.php";
    public static String GET_PREGUNTAS_BY_FACTOR = BASE_URL+"preguntas/select_by_factor.php";
    public static String GET_PROGRESO_BY_USER=BASE_URL+"progress/select_progress_by_id.php";
    public static String GET_GRAPHS_DATA=BASE_URL+"response/result_by_user.php";
    public static String SEND_ANSWER=BASE_URL+"progress/update.php";
    public static String SELECT_STUDENTS=BASE_URL+"progress/select.php";
}

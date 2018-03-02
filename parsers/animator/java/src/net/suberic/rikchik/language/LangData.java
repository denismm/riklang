package net.suberic.rikchik.language;

public class LangData{

    public final static int COLLECTOR_NUM = 37;
    public final static int RELATION_NUM = 14;
    public final static int FORM_NUM = 7;

    public final static String ARC_TENTACLE_NAME = "arc";
    public final static String CIRCLE_TENTACLE_NAME = "circle";
    public final static String ELLARC_TENTACLE_NAME = "ellarc";
    public final static String FISHBEND_TENTACLE_NAME = "fishbend";
    public final static String GREATARC_TENTACLE_NAME = "greatarc";
    public final static String HALFHOOK_TENTACLE_NAME = "halfhook";
    public final static String HOOK_TENTACLE_NAME = "hook";
    public final static String LBEND_TENTACLE_NAME = "lbend";
    public final static String LINE_TENTACLE_NAME = "line";
    public final static String LOBE_TENTACLE_NAME = "lobe";
    public final static String SQUIGGLE_TENTACLE_NAME = "squiggle";
    public final static String WICKET_TENTACLE_NAME = "wicket";
    public final static String ZIGZAG_TENTACLE_NAME = "zigzag";

    /* collectors */
    public final static int C_0 = 0;
    public final static int C_1 = 1;
    public final static int C_2 = 2;
    public final static int C_3 = 3;
    public final static int C_4 = 4;
    public final static int C_5 = 5;
    public final static int C_6 = 6;
    public final static int C_7 = 7;
    public final static int C_S = 8;

    /* pronomials */
    public final static int P_NULL= 0;
    public final static int P_P = 14;
    public final static int P_O = 28;

    /* relations */
    public static final int R_AGENT = 0;
    public static final int R_SOURCE = 1;
    public static final int R_INSTRUMENT = 2;
    public static final int R_QUALITY = 3;
    public static final int R_DESTINATION = 4;
    public static final int R_PATIENT = 5;
    public static final int R_INCLUDES = 6;
    public static final int R_TASK = 7;
    public static final int R_RESULT = 8;
    public static final int R_EXAMPLE = 9;
    public static final int R_MEANS = 10;
    public static final int R_ACTOR = 11;
    public static final int R_ELEMENT = 12;
    public static final int R_END = 13;

    /* forms */
    public static final int F_R = 0;
    public static final int F_T = 1;
    public static final int F_V = 2;
    public static final int F_M = 3;
    public static final int F_N = 4;
    public static final int F_P = 5;
    public static final int F_I = 6;

    public static int getInflectionValue (String name){
	if (name.equalsIgnoreCase ("0")) {
	    return C_0;
	} else if (name.equalsIgnoreCase ("1")) {
	    return C_1;
	} else if (name.equalsIgnoreCase ("2")) {
	    return C_2;
	} else if (name.equalsIgnoreCase ("3")) {
	    return C_3;
	} else if (name.equalsIgnoreCase ("4")) {
	    return C_4;
	} else if (name.equalsIgnoreCase ("5")) {
	    return C_5;
	} else if (name.equalsIgnoreCase ("6")) {
	    return C_6;
	} else if (name.equalsIgnoreCase ("7")) {
	    return C_7;
	} else if (name.equalsIgnoreCase ("S")) {
	    return C_S;
	} else if (name.equalsIgnoreCase ("AGENT")) {
	    return R_AGENT;
	} else if (name.equalsIgnoreCase ("SOURCE")) {
	    return R_SOURCE;
	} else if (name.equalsIgnoreCase ("INSTRUMENT")) {
	    return R_INSTRUMENT;
	} else if (name.equalsIgnoreCase ("QUALITY")) {
	    return R_QUALITY;
	} else if (name.equalsIgnoreCase ("DESTINATION")) {
	    return R_DESTINATION;
	} else if (name.equalsIgnoreCase ("PATIENT")) {
	    return R_PATIENT;
	} else if (name.equalsIgnoreCase ("INCLUDES")) {
	    return R_INCLUDES;
	} else if (name.equalsIgnoreCase ("TASK")) {
	    return R_TASK;
	} else if (name.equalsIgnoreCase ("RESULT")) {
	    return R_RESULT;
	} else if (name.equalsIgnoreCase ("EXAMPLE")) {
	    return R_EXAMPLE;
	} else if (name.equalsIgnoreCase ("MEANS")) {
	    return R_MEANS;
	} else if (name.equalsIgnoreCase ("ACTOR")) {
	    return R_ACTOR;
	} else if (name.equalsIgnoreCase ("ELEMENT")) {
	    return R_ELEMENT;
	} else if (name.equalsIgnoreCase ("END")) {
	    return R_END;
	} else if (name.equalsIgnoreCase ("R")) {
	    return F_R;
	} else if (name.equalsIgnoreCase ("T")) {
	    return F_T;
	} else if (name.equalsIgnoreCase ("V")) {
	    return F_V;
	} else if (name.equalsIgnoreCase ("M")) {
	    return F_M;
	} else if (name.equalsIgnoreCase ("N")) {
	    return F_N;
	} else if (name.equalsIgnoreCase ("P")) {
	    return F_P;
	} else if (name.equalsIgnoreCase ("I")) {
	    return F_I;
	} else {
	    return -1;
	}
    }

}



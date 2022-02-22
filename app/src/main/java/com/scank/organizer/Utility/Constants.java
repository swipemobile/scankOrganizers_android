package com.scank.organizer.Utility;


import com.scank.organizer.BuildConfig;

public class Constants {


    public static final String SHOW = "show";
    public static final String HIDE = "hide";

    public static String internetmsg = "The internet seems to be unavailable. " +
            "Please check your connection and try again";


    public static final String registerLink = "https://scank.azurewebsites.net/Register";

    public static String AUTH_TOKEN;

    public static final String EMPTY_STRING = "N/A";
    public static int permissionDialogCounter = 0;

    /**
     * Server type for change base url for debugger applicaiton
     */
    public static final class SERVER_TYPE {
        public static final int DEVELOPMENT = 1;
        public static final int STAGING = 2;
    }

    /**
     * <b>Description</b> - Keys for all API Calling
     *
     * @author sarfaraj
     */
    public static final class JSON_KEY {

        public static final String VDEVICE_TYPE = "vDeviceType";
        public static final String VDEVICE_ID = "vDeviceID";
        public static final String VUDID = "vudid";
        public static final String VAPP_VERSION = "vAppVersion";

        // signup
        public static final String MESSAGE = "message";
        public static final String STATUS = "status";
        public static final String DATA = "data";
        public static final String ERRORS = "errors";
        public static final String UNAUTHORIZE = "unauthoize";
        public static final String CONTENT_TYPE = "accept";

        public static final String _ID = "id";
        public static final String VEMAIL_ID = "vEmailID";
        public static final String VPASSWORD = "vPassword";
        public static final String PASSWORD = "password";


        public static final String VNAME = "vName";
        public static final String DDOB = "ddob";

        public static final String VFIRST_NAME = "vFirstName";
        public static final String VLAST_NAME = "vLastName";
        public static final String VSURNAME = "vSurname";
        public static final String VSTATUS = "vStatus";

    }

    public static final class REQUEST_CODE {

        public static final int SCAN_CODE = 1003;

        public static final int LOGIN_CALL = 1001;
        public static final int LOGOUT_CALL = 1002;

        public static final int RESULT_LOAD_IMG_GALLERY = 1005;
        public static final int RESULT_LOAD_IMG_CAMERA = 1006;
        public static final int RESULT_PIC_CROP = 1007;
        public static final int IMAGE_CROP = 1008;

        public static final int RESULT_SESSION_END = 1009;
        public static final int RESULT_SEND_TO_CHAT_SCREEN = 1010;

        public static final int OVERLAY_PERMISSION_REQ_CODE = 3019;

        public static final int REQUEST_ALL_PERMISSION = 200;

        public static final int SELECT_PHOTO = 99;
        public static final int REQUEST_VIDEO_CAPTURE = 100;
        public static final int REQUEST_CAMERA_CAPTURE = 101;

        public static final int REQUEST_PERMISSION_SETTING = 98;
        public static final int MEDIA_PROJECTION_REQUEST_CODE = 13;
        public static final int FORGOT_PASSWORD = 14;



    }

    /**
     * This class contains request permission request code
     */
    public static final class REQUEST_PERMISSION {
        public static final int READ_STORAGE = 101;
        public static final int WRITE_STORAGE = 102;
        public static final int VIDEO_CALLING = 103;
        public static final int CAMERA = 104;
    }

    /**
     * This class contains intent and bundle keys
     */
    public static final class INTENT_KEY {
        //This key use for put condition for animate activity
        public static final String LOGIN = "Login";

        public static final String EVENT_DATA = "event_data";
        public static final String TAB_ID = "tabId";

        public static final String KEY_ATTACHMENTS = "attachments";
        public static final String KEY_ITEM_REMOVE = "item_remove";

        public static final String EMAIL = "Email";

        public static final String USER_ID = "ID";
        public static final String PASSWORD = "Password";
        public static final String IS_REDIRECT_FROM_HOME = "isRedirectFromHome";

        public static final String IS_AUTHENTICATION_CHECK = "isAuthenticateCheck";

        public static final String SCAN_TYPE = "scan_type";
        public static final String TICKET_DATA = "ticket_data";
        public static final String TOTAL_GUEST = "total_guest";
        public static final String CHECKED_IN_GUEST = "checked_in_guest";
        public static final String CHECKED_IN_RATIO = "checked_in_ratio";

    }

    /**
     * This class contains api request tag for identify response call back
     */
    public static final class REQUEST_QUEUE_TAG {
        public static final String FORGOT_PASSWORD = "FORGOT_PASSWORD";
        public static final String GET_TOKEN = "GET_TOKEN";
        public static final String LOGIN = "LOGIN";
        public static final String LOGOUT_UNAUTHORIZED = "LOGOUT_UNAUTHORIZED";

        public static final String AUTHENTICATION = "AUTHENTICATION";

    }

    public static final class HTTP_STATUS_CODE {
        public static final int NOT_FOUND = 404;
        public static final int BAD_REQUEST = 400;
        public static final int UNAUTHORIZED = 401;
        public static final int INTERNAL_SERVER_ERROR = 500;
        public static final int SERVICE_UNAVAILABLE = 503;
    }

    public static final class NOTIFICATION_ID {
        public static final int ACTIVE_CALL = 101;
        public static final int ONGOING_SERVICE = 111;
    }

    /**
     * @Description Date Format String
     */
    public static final class DATE_FORMAT {

        public static final String DATE_YMD_TIME_HMS_DEFAULT = "yyyy-MM-dd'T'HH:mm:ss";

        /**
         * @Format "yyyy-MM-dd HH:mm:ss Z"
         * @Example 2012-01-23 12:24:45 +0530
         */
        public static final String DATE_YMD_TIME_HMS_ZONEFORMAT = "yyyy-MM-dd HH:mm:ss Z";

        /**
         * @Format "yyyy-MM-dd HH:mm:ss" (0 - 23 hours)
         * @Example 2017-01-23 00:24:45
         */
        public static final String DATE_YMD_TIME_HMS = "yyyy-MM-dd HH:mm:ss";

        public static final String DATE_DMY_TIME_HMS = "dd/MM/yyyy HH:mm:ss";

        /**
         * @Format "yyyy-MM-dd kk:mm:ss"
         * @Example 2012-01-23 12:24:45
         */
        public static final String DATE_YMD_TIME_KMS = "yyyy-MM-dd kk:mm:ss";

        /**
         * @Format "dd MMM yyyy"
         * @Example 23 Jan 2012
         */
        public static final String DATE_DMY = "dd MMM, yyyy";

        /**
         * @Format "EEE, MM/dd/yyyy - HH:mm"
         * @Example Wed, 10/30/2013 - 07:01
         */
        public static final String DATE3_DAY_MDY_TIME_HM = "EEE, MM/dd/yyyy - HH:mm";

        /**
         * @Format "dd MMM yyyy HH:mm:ss"
         * @Example 23 Jan 2012 10:10:10
         */
        public static final String DATE_DMY_MONTH_ALPHA_TIME_HMS = "dd MMM yyyy HH:mm:ss";

        /**
         * -@Format "MM/dd/yyyy KK:mm" (12 Hours from 0 - 11)
         *
         * @Example 01/23/2012 00:24
         */
        public static final String DATE_MDY_TIME_KM_A = "MM/dd/yyyy KK:mm a";

        /**
         * @Format "MM/dd/yyyy hh:mm" (12 Hours from 1 - 12)
         * @Example 01/23/2012 12:24
         */
        public static final String DATE_MDY_TIME_HM_A = "MM/dd/yyyy hh:mm a";

        /**
         * @Format "MM/dd/yyyy hh:mm" (12 Hours from 1 - 12)
         * @Example 01/23/2012 12:24
         */
        public static final String DATE_TIME_HM_A_MDY = "hh:mm a MM/dd/yyyy";

        /**
         * @Format "KK:mm a dd MMM, yy" (12 Hours from 0 - 12)
         * @Example 00:24 AM-09 Feb, 16
         */
        public static final String TIME_HM_A_DATE_DMY = "hh:mm a-dd MMM, yy";

        /**
         * @Format "MM/dd/yyyy HH:mm:ss" (24 Hours from 0 - 23)
         * @Example 11/23/2017 00:11:24
         */
        public static final String DATE_MDY_TIME_HMS = "MM/dd/yyyy HH:mm:ss";

        /**
         * @Format "EEEE, MMMM dd, yyyy HH:mm"
         * @Example Monday, January 23, 2012 12:24
         */
        public static final String DAY_DATE_MDY_MONTH_ALPHA_TIME_HM = "EEEE, MMMM dd, yyyy HH:mm";

        /**
         * @Format "yyyy-MM-dd HH:mm:ss"
         * @Example 2013-11-13 00:11:24
         */
        public static final String DAY_DATE_YMD_HMS = "yyyy-MM-dd HH:mm:ss";


        /**
         * @Format "yyyy-MM-dd"
         * @Example 2013-11-13
         */
        public static final String DAY_DATE_YMD = "yyyy-MM-dd";

        /**
         * @Format "dd/MM/yyyy"
         * @Example 14/02/2017"
         */
        public static final String DAY_DATE_DMY = "dd/MM/yyyy";

        /**
         * TODO: Nikhil, we are following mm/dd/yyyy
         *
         * @Format "MM-dd-yyyy"
         * @Example 02-20-2017
         */
        public static final String DAY_DATE_MMDDYY = "MM-dd-yyyy";

        /**
         * @Format "MM/dd/yyyy"
         * @Example 02/14/2017"
         */
        public static final String DAY_DATE_MDY = "MM/dd/yyyy";

        /**
         * @Format "HH:mm a" (24 Hours)
         * @Example 12:34 PM
         */
        public static final String TIME_HH_MM_A = "HH:mm a";

        /**
         * @Format "HH:mm:ss (24 Hours)
         * @Example 12:34:45
         */
        public static final String TIME_24_HH_MM_SS = "HH:mm:ss";

        /**
         * @Format "hh:mm:ss (12 Hours)
         * @Example 12:34:45
         */
        public static final String TIME_12_HH_MM_SS = "hh:mm:ss";


        /**
         * @Format "hh:mm a" (12 Hours, 1 - 12)
         * @Example 12:34 PM
         */
        public static final String TIME_HM_A_12 = "hh:mm a";

        /**
         * @Format "yyyyMMdd-hh:mm a" (12 Hours, 1 - 12)
         * @Example 20170521-12:34 PM
         */
        public static final String DATE_DASH_TIME_HM_A_12 = "yyyyMMdd-hh:mm a";

        /**
         * @Format "MMM dd, yyyy"
         * @Example Dec 08, 2016
         */
        public static final String DATE_MONTH_SORT_ALPHA_DATE_YEAR = "MMM dd, yyyy";

    }

}

package com.dailylocally.utilities;

import com.dailylocally.BuildConfig;
import com.dailylocally.data.prefs.AppPreferencesHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AppConstants {


  public static final int API_STATUS_CODE_LOCAL_ERROR = 0;


  public static final int NULL_INDEX = 0;

  public static final String PREF_NAME = "dailylocally_pref";

  public static final String SEED_DATABASE_OPTIONS = "seed/options.json";

  public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

  public static final String STATUS_CODE_FAILED = "failed";

  public static final String STATUS_CODE_SUCCESS = "success";

  public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

  public static final int REFUND_LIST_CODE = 1110;
  public static final int COUPON_LIST_CODE = 1111;
  public static final int SELECT_ADDRESS_LIST_CODE = 1122;


  public static final String FCM_RECEIVER = "ALERT";


  public static final String FCM_RECEIVER_ORDER = "ORDER RECEIVER";


  public static final String CURRENT_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


  public static final String PACKAGE_NAME = "com.tovo.sales";

  public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

  /**
   * Used to set an expiration time for a geofence. After this amount of time Location Services
   * stops tracking the geofence.
   */
  public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
  /**
   * For this sample, geofences expire after twelve hours.
   */
  public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
          GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
  public static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km
  /**
   * Map for storing information about airports in the San Francisco bay area.
   */
    /*public  static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<>();
     static {
        // San Francisco International Airport.
        BAY_AREA_LANDMARKS.put("SFO", new LatLng(37.621313, -122.378955));
        // Googleplex.
        BAY_AREA_LANDMARKS.put("GOOGLE", new LatLng(37.422611,-122.0840577));
    }*/


  //Promotion
  public static final String PROMOTION_URL = "PROMOTION_URL";
  public static final String PROMOTION_ID = "PROMOTION_ID";
  public static final String PROMOTION_TYPE = "PROMOTION_TYPE";


  public static final int TYPE_MALE = 1;
  public static final int TYPE_FEMALE = 2;
  public static final String ADDRESS_TYPE_HOME = "1";
  public static final String ADDRESS_TYPE_WORK = "2";
  public static final String ADDRESS_TYPE_OTHER = "3";
  public static final int QUERY_TYPE_ORDER = 1;
  public static final int QUERY_TYPE_GENERAL = 2;
  public static final int QUERY_TYPE_ORDER_HISTORY = 3;

  //  public static final String SCREEN_CHANGE_KITCHEN = "Change kitchen alert";
  public static final int KITCHEMN_REQUEST_CODE = 1;
  public static final int KITCHEMN_APPLIANCES_REQUEST_CODE = 2;
  public static final int PACKAGING_IMAGE_REQUEST_CODE = 3;
  public static final int SIGNATURE_REQUEST_CODE = 4;
  public static final int GPS_REQUEST = 500;
  public static final int GPS_NORMAL_REQUEST = 5522;
  public static final int HOME_ADDRESS_CODE = 5556;
  public static final int CART_REQUESTCODE = 111;
  public static final int COD_REQUESTCODE = 222;
  public static final int ONLINE_REQUESTCODE = 333;
  public static final int INTERNET_ERROR_REQUEST_CODE = 4404;
  public static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 501;


  public static final String API_VERSION_ONE = "1.0.0";
  public static final String API_VERSION_TWO = "2.0.0";
  public static final String API_VERSION_TWO_ONE = "2.0.1";
  public static final String API_VERSION_TWO_TWO = "2.0.2";
  public static final String API_VERSION_THEREE = "3.0.0";
  public static final String API_VERSION_FOUR = "4.0.0";
  public static final String APP_TYPE_ANDROID = "1";// 1 means android
  public static final String ANALYTICYS_PRODUCT_ID = "product_id";
  public static final String ANALYTICYS_PRODUCT_NAME = "product_name";
  public static final String ANALYTICYS_PRODUCT_QUANTITY = "product_quantity";


  //Analytics param key
  public static final String ANALYTICYS_PRODUCT_PRICE = "product_price";
  public static final String ANALYTICYS_MOBILE_NUMBER = "mobile_number";
  public static final String ANALYTICYS_ORDER_ID = "order_id";
  public static final String ANALYTICYS_PRICE = "price";
  public static final String ANALYTICYS_USER_ID = "user_id";
  public static final String ANALYTICYS_USER_NAME = "user_name";
  public static final String ANALYTICYS_STORY_ID = "story_id";
  public static final String ANALYTICYS_STORY_TITLE = "story_title";
  //  public static final String ANALYTICYS_REGION = "region_name";
  public static final String ANALYTICYS_SEARCH_TYPE = "search_type";
  public static final String ANALYTICYS_SEARCH_NAME = "search_name";
  public static final String ANALYTICYS_SEARCH_SUGGESTION = "suggestion";
  public static final String ANALYTICYS_SEARCH_ID = "search_id";
  public static final String ANALYTICYS_KITCHEN_NAME = "category_name";
  public static final String ANALYTICYS_KITCHEN_ID = "category_id";
  public static final String CLICK_KITCHEN_VIEW_CART = "category_view_cart";
  public static final String CLICK_FAV_VIEW_CART = "fav_view_cart";
  public static final String CLICK_COLLECTION_VIEW_CART = "collection_view_cart";
  public static final String CLICK_DIRECT_VIEW_CART = "direct_view_cart";
  public static final String ANALYTICYS_HOME_KITCHEN = "home_category";
  public static final String ANALYTICYS_FAV_KITCHEN = "fav_category";
  public static final String ANALYTICYS_REGION_KITCHEN = "region_category";
  public static final String ANALYTICYS_COLLECTION_KITCHEN = "collection_category";
  public static final String ANALYTICYS_CART_KITCHEN = "cart_category";
  public static final String ANALYTICYS_SEARCH_KITCHEN = "search_category";
  //Analytics param event key
  public static final String ANALYTICYS_ADD_TO_CART = "add_to_cart";
  public static final String ANALYTICYS_REMOVE_FROM_CART = "remove_from_cart";
  public static final String ANALYTICYS_CURRENCY_TYPE = "currency";
  public static final String ANALYTICYS_CURRENCY = "INR";
  public static final String ANALYTICYS_PAYMENT_SUCCESS = "online_payment_success";
  public static final String ANALYTICYS_PAYMENT_FAILED = "online_payment_failed";
  public static final String ANALYTICYS_USER_LOGIN = "user_login";
  public static final String ANALYTICYS_STORY_VIEW = "story";
  public static final String ANALYTICYS_SEARCH_CLICKED = "search";
  public static final String ANALYTICYS_PROCEED_TO_PAY = "proceed_to_pay";
  public static final String ANALYTICYS_CHECKOUT = "checkout";
  public static final String ANALYTICYS_CREATE_ORDER = "create_order";
  public static final String ANALYTICYS_ORDER_PLACED = "order_placed";
  public static final String ANALYTICYS_REGION_SELECTED = "region_selected";
  public static final String ANALYTICYS_RATING = "rating";
  public static final String ANALYTICYS_feedback = "feedback";
  public static final String ANALYTICYS_CHAT_MESSAGE = "chat_message";
  public static final String ANALYTICYS_QUERIES = "query";
  public static final String ANALYTICYS_MAKE_QUERIES = "make_query";
  public static final String ANALYTICYS_REPEAT_ORDER = "repeat_order";
  //analytics screen name
  public static final String SCREEN_EDIT_MYACCOUNT = "edit_my_account";
  public static final String SCREEN_FAVOURITE_DISH = "favourite_dish";
  public static final String SCREEN_FAVOURITE_KITCHEN = "favourite_category";
  public static final String SCREEN_FAVOURITES = "favourites";
  public static final String SCREEN_APP_FEEDBCK = "app_feedback";
  public static final String SCREEN_SUPPORT_REPLIES = "support_replies";
  public static final String SCREEN_QUERY_CHAT = "queries_chat";
  public static final String SCREEN_SUPPORT = "support";
  public static final String SCREEN_FEEDBACK_SUPPORT = "feedback_and_support_page";
  public static final String SCREEN_ORDER_LIST = "orders_list";
  public static final String SCREEN_ORDER_DETAILS = "orders_details";
  public static final String SCREEN_PAYMENT_RETRY = "payment_retry";
  public static final String SCREEN_ORDER_PLACED = "order_placed";
  public static final String SCREEN_FUNNEL_COUPON = "funnel_coupon_page";
  public static final String SCREEN_REFERRAL = "referral";
  public static final String SCREEN_MY_ACCOUNT = "my_account";
  public static final String SCREEN_MY_ORDERS = "my_orders";
  public static final String SCREEN_ADD_ADDRESS = "add_address";
  public static final String SCREEN_EDIT_ADDRESS = "edit_address";
  public static final String SCREEN_ADDRESS_LIST = "address_list";
  public static final String SCREEN_ORDER_CANCELED = "order_canceled_popup";
  public static final String SCREEN_COUPON_LIST = "coupon_list";
  public static final String SCREEN_REFUND_ALERT = "refund_amount_excess_popup";
  public static final String SCREEN_CART_PAGE = "cart_page";
  public static final String SCREEN_KITCHEN_FILTER = "category_filter";
  public static final String SCREEN_X_FACTOR = "DELIVERY_DEMAND";
  public static final String SCREEN_STORIES = "stories";
  public static final String SCREEN_HOME = "home";
  public static final String SCREEN_REGION_DETAILS = "region_details";
  public static final String SCREEN_REGION_LIST = "region_list";
  public static final String SCREEN_KITCHEN_DETAILS = "category_details";
  public static final String SCREEN_ONBOARDING = "on_boarding";
  public static final String SCREEN_ORDER_RATING = "order_rating";
  public static final String SCREEN_PAYMENT = "payment";
  public static final String SCREEN_GET_EMAIL = "get_email";
  public static final String SCREEN_EXPLORE_COLLECTION = "explore_collection";
  public static final String SCREEN_SEARCH = "page_search";
  public static final String SCREEN_FAQS_AND_SUPPORT = "faqs_support";
  public static final String SCREEN_FAQS = "faqs";
  public static final String SCREEN_PRIVACY_POLICY = "privacy_policy";
  public static final String SCREEN_USER_REGISTRATION = "user_registration";
  public static final String SCREEN_OTP = "OTP";
  public static final String SCREEN_TERMS_CONDITION = "terms_and_condition";
  public static final String SCREEN_LOGIN = "login";
  public static final String SCREEN_FORCE_UPDATE = "force_update";
  public static final String SCREEN_ORDER_HELP = "order_help";
  public static final String SCREEN_CURRENT_ORDER_DETAILS = "current_order_details";
  public static final String SCREEN_CURRENT_ORDER_TRACKING = "current_order_tracking";
  public static final String SCREEN_VIEW_IMAGE = "view_image";
  public static final String SCREEN_KITCHEN_LIST = "category_list";
  public static final String CLICK_REGION_CHANGE = "change_region";
  public static final String CLICK_REGION_OTHER = "region_other";
  public static final String CLICK_APPLY_CHANGES = "profile_update";
  public static final String SCREEN_RAZOR_PAY = "razor_pay";
  public static final String SCREEN_DIAL = "dial";


//analytics screen click

  //edit my account
  public static final String CLICK_BACK_BUTTON = "back_click";
  public static final String CLICK_MALE_SELECTED = "male_selected";
  public static final String CLICK_FEMALE_SELECTED = "female_selected";
  public static final String CLICK_CHANGE_KITCHEN_CONFIRM = "change_category_confirm";
  public static final String CLICK_CHANGE_KITCHEN_CANCEL = "change_category_cancel";
  public static final String CLICK_REFRESH = "refresh";
  public static final String CLICK_KITCHEN_CLICK = "category_click";
  public static final String CLICK_KITCHEN_MENU = "category_menu";
  public static final String CLICK_DISH_MENU = "dish_menu";
  public static final String CLICK_SEND = "send";
  public static final String CLICK_CALL_SUPPORT = "call_support";
  public static final String CLICK_QUERY_SUBMIT = "query_submit";
  public static final String CLICK_REPLIES = "replies";
  public static final String CLICK_FEEDBACK = "feedback";
  public static final String CLICK_SUPPORT = "support";
  public static final String CLICK_FAQ = "faqs";
  public static final String CLICK_VIEW_DETAILS = "view_details";
  public static final String CLICK_OREDER_HISTORY_PLACE_FIRST_ORDER = "orders_history_place_first_order";
  public static final String CLICK_REPEAT_THIS_ORDER = "repeat_this_order";
  public static final String CLICK_SEND_REFERRAL = "send_referral";
  public static final String CLICK_EDIT = "edit";
  public static final String CLICK_MANAGE_ADDRESS = "manage_address";
  public static final String CLICK_REFERRALS = "referrals";
  public static final String CLICK_ORDER_HISTORY = "order_history";
  public static final String CLICK_FEEDBACK_SUPPORT = "feedback_and_support";
  public static final String CLICK_LOGOUT = "logout";
  public static final String CLICK_SAVE = "save";
  public static final String CLICK_DELETE = "delete";
  public static final String CLICK_ADDRESS_HOME = "address_home";
  public static final String CLICK_ADDRESS_OTHER = "address_other";
  public static final String CLICK_ADDRESS_WORK = "address_work";
  public static final String CLICK_ADDRESS_CURRENT_LOCATION = "current_location";
  public static final String CLICK_ADD_NEW_ADDRESS = "add_new_address";
  public static final String CLICK_SELECT_ADDRESS = "select_address";
  public static final String CLICK_CLOSE = "close";
  public static final String CLICK_APPLY = "apply";
  public static final String CLICK_SELECT = "select";
  public static final String CLICK_CANCEL = "cancel";
  public static final String CLICK_YES = "yes";
  public static final String CLICK_NO = "no";
  public static final String CLICK_CHANGE_ADDRESS = "change_address";
  public static final String CLICK_REFUND_CHECK = "refund_check";
  public static final String CLICK_REFUND_UNCHECK = "refund_uncheck";
  public static final String CLICK_REFUND_SELECT = "refund_select";
  public static final String CLICK_EXPLORE_KITCHEN = "explore_category";
  public static final String CLICK_GO_HOME = "go_home";
  public static final String CLICK_ADD_PROMO_CODE = "add_promo_code";
  public static final String CLICK_PROCEED_TO_PAY = "proceed_to_pay";
  public static final String CLICK_ADD_QUANTITY = "add_quantity";
  public static final String CLICK_REMOVE_QUANTITY = "remove_quantity";
  public static final String CLICK_CLEAR = "clear";
  public static final String CLICK_SORT = "sort";
  public static final String CLICK_CUISINE = "cuisine";
  public static final String CLICK_NEXT = "next";
  public static final String CLICK_PREVIOUS = "previous";
  public static final String CLICK_HOLD = "hold";
  public static final String CLICK_SEE_MORE = "see_more";
  public static final String CLICK_STORY = "click_story";
  public static final String CLICK_FAVOURITES = "favourites";
  public static final String CLICK_REGION_CARD = "region_card";
  public static final String CLICK_REGION_EXPLORE_ALL = "explore_all_ region";
  public static final String CLICK_FILTER = "filter";
  public static final String CLICK_KITCHEN = "category";
  public static final String CLICK_ALLOW_LOACTION = "allow_location";
  public static final String CLICK_GPS_ON = "gps_on";
  public static final String CLICK_GPS_NO_THANKS = "no_thanks";
  public static final String CLICK_COLLECTION = "collection";
  public static final String CLICK_COUPON = "coupon";
  public static final String CLICK_SEARCH = "home_search";
  public static final String CLICK_CART = "cart";
  public static final String CLICK_MY_ACCOUNT = "my_account";
  public static final String CLICK_ORDER_TRACK = "track_order";
  public static final String CLICK_ORDER_RATING = "order_rating";
  public static final String CLICK_EXIT_APP = "exit_app";
  public static final String CLICK_PAYMENT_RETRY = "payment_retry";
  public static final String CLICK_PAYMENT_CANCEL = "payment_cancel";
  public static final String CLICK_VIEW_KITCHEN = "view_category";
  public static final String CLICK_VEG_ONLY = "veg_only";
  public static final String CLICK_MENU = "menu";
  public static final String CLICK_INFO = "info";
  public static final String CLICK_VIEW_CART = "view_cart";
  public static final String CLICK_VIEW_IMAGE = "view_image";
  public static final String CLICK_GET_STARTED = "get_started";
  public static final String CLICK_SUBMIT = "submit";
  public static final String CLICK_NOT_NOW = "not_now";
  public static final String CLICK_COD = "cash_on_delivery";
  public static final String CLICK_ONLINE = "online_banking";
  public static final String CLICK_VIEW_MENU = "view_menu";
  public static final String CLICK_CHANGE_KITCHEN_YES = "change_category_yes";
  public static final String CLICK_CHANGE_KITCHEN_NO = "change_category_no";
  public static final String CLICK_MALE = "male";
  public static final String CLICK_FEMALE = "female";
  public static final String CLICK_PROCEED = "proceed";
  public static final String CLICK_CONTINUE = "continue";
  public static final String CLICK_RESEND = "resend_otp";
  public static final String CLICK_ACCEPT_AND_CONTINUE = "accept_and_continue";
  public static final String CLICK_VERIFY = "verify";
  public static final String CLICK_ACCEPT_CHECK_BOX = "accept_check_box";
  public static final String CLICK_UPDATE = "update";
  public static final String CLICK_CALL_DELIVERY_EXECUTIVE = "call_delivery_executive";
  public static final String CLICK_CONTACT_SUPPORT = "contact_support";
  public static final String CLICK_ORDER_CANCEL = "order_cancel";
  public static final String CLICK_HELP = "help";
  public static final String CLICK_ORDER_DETAILS = "order_details";
  public static final String CLICK_ADD_TO_FAV = "add_to_favourite";
  public static final String CLICK_REMOVE_FROM_FAV = "remove_from_favourite";

  ///////Server Ip Ports
  // public static final String URL_SERVER_IP_PORT = "http://eatalltime.co.in";

  public static final String URL_SERVER_IP_PORT = BuildConfig.BASE_URL;////live ip port new
  public static final String URL_SIGN_UP = URL_SERVER_IP_PORT + "/eat/login";/////POST method
  public static final String URL_CATEGORY_LIST = URL_SERVER_IP_PORT + "/user/categorylist";/////POST method



  public static final String URL_OTP_VERIFICATION = URL_SERVER_IP_PORT + "/eat/otpverification";/////POST method
  public static final String URL_NAME_GENDER_INSERT = URL_SERVER_IP_PORT + "/eat/edit";/////put method
  public static final String URL_APP_FEEDBACK = URL_SERVER_IP_PORT + "/eat/feedback";/////POST method
  public static final String URL_LOGIN_MAIN = URL_SERVER_IP_PORT + "/eat/checklogin";/////POST method
  public static final String URL_QUERY_INSERT = URL_SERVER_IP_PORT + "/query";/////POST method
  public static final String URL_QUERY_REPLIES_COUNT = URL_SERVER_IP_PORT + "/repliescount";/////POST method
  public static final String URL_QUERY_QUERIES_LIST = URL_SERVER_IP_PORT + "/querylist";/////POST method
  public static final String URL_REPLIES_CHAT = URL_SERVER_IP_PORT + "/queryreplies/";/////GET method
  public static final String URL_CHAT_ANSWER = URL_SERVER_IP_PORT + "/queryanswer/";/////GET method
  public static final String URL_CHAT_REPLIES_READ = URL_SERVER_IP_PORT + "/repliesread/";/////GET method
  public static final String URL_ORDERS_HISTORY_LIST = URL_SERVER_IP_PORT + "/eat/orders/";/////GET method
  public static final String URL_ORDERS_HISTORY_VIEW = URL_SERVER_IP_PORT + "/eat/order/";/////GET method
  public static final String URL_REFERRALS = URL_SERVER_IP_PORT + "/eat/referral/";/////GET method
  public static final String URL_REGISTRATION = URL_SERVER_IP_PORT + "/eat/postregistration";/////GET method
  public static final String URL_REGION_LIST = URL_SERVER_IP_PORT + "/masters/regionlist";/////GET method
  public static final String URL_FORGOT_PASSWORD = URL_SERVER_IP_PORT + "/eat/forgot";/////GET method
  public static final String URL_SET_CONFIRM_PASSWORD = URL_SERVER_IP_PORT + "/eat/password";/////GET method
  public static final String URL_ORDER_RATING = URL_SERVER_IP_PORT + "/eat/rating";/////POST method
  public static final String URL_ORDER_RATING_SKIP = URL_SERVER_IP_PORT + "/eat/orderskip";/////POST method
  public static final String URL_GET_USER_DETAILS = URL_SERVER_IP_PORT + "/eatusers/";/////POST method0
  public static final String URL_LOG_OUT = URL_SERVER_IP_PORT + "/eat/logout";/////POST method
  public static final String URL_CANCEL_ORDER = URL_SERVER_IP_PORT + "/eat/order/cancel/";/////POST method
  public static final String EAT_KITCHEN_LIST_URL = URL_SERVER_IP_PORT + "/eat/kitchenlist";
  public static final String EAT_DISH_LIST_URL = URL_SERVER_IP_PORT + "/eat/dishlist";
  public static final String EAT_SEARCH_DISH_LIST_URL = URL_SERVER_IP_PORT + "/eat/product/search";
  public static final String EAT_FAV_DISH_LIST_URL = URL_SERVER_IP_PORT + "/eat/fav/dishlist/";
  public static final String EAT_FAV_KITCHEN_LIST_URL = URL_SERVER_IP_PORT + "/eat/fav/kitchenlist/";
  public static final String EAT_KITCHEN_DISH_LIST_URL = URL_SERVER_IP_PORT + "/eat/products";
  public static final String EAT_ADD_ADDRESS_URL = URL_SERVER_IP_PORT + "/eat/address";
  public static final String EAT_GET_ADDRESS_URL = URL_SERVER_IP_PORT + "/eat/addresslist/";
  public static final String EAT_DELETE_ADDRESS_URL = URL_SERVER_IP_PORT + "/eat/addressdelete/";
  public static final String EAT_ADD_ADDRESS_LIST_URL = URL_SERVER_IP_PORT + "/eat/address/";
  public static final String EAT_FUNNEL_URL = URL_SERVER_IP_PORT + "/eat/tunnelorder";
  public static final String EAT_REFUND_LIST_URL = URL_SERVER_IP_PORT + "/eat/refund/";
  public static final String EAT_COUPON_LIST_URL = URL_SERVER_IP_PORT + "/eat/coupon/";
  public static final String EAT_COUPON_CHECK_URL = URL_SERVER_IP_PORT + "/eat/coupon/validate/";
  public static final String URL_FAQS = URL_SERVER_IP_PORT + "/faqs/";/////PUT method0
  public static final String EAT_CART_DETAILS_URL = URL_SERVER_IP_PORT + "/eat/cartdetails/";
  public static final String EAT_COLLECTION_DETAILS_URL = URL_SERVER_IP_PORT + "/eat/collectiondetails/";
  public static final String EAT_CREATE_ORDER_URL = URL_SERVER_IP_PORT + "/eat/proceedtopay";
  public static final String EAT_ORDER_DETAILS_URL = URL_SERVER_IP_PORT + "/eat/order/";
  public static final String EAT_ORDER_ETA = URL_SERVER_IP_PORT + "/eat/order/deliverytime";
  public static final String EAT_LIVE_ORDER_URL = URL_SERVER_IP_PORT + "/eat/liveorders/";
  public static final String EAT_FAV_URL = URL_SERVER_IP_PORT + "/eat/fav/";
  public static final String EAT_FCM_TOKEN_URL = URL_SERVER_IP_PORT + "/eat/pushid/add";
  public static final String EAT_FCM_FORCE_UPDATE = URL_SERVER_IP_PORT + "/eat/versioncheck";
  public static final String EAT_DEFAULT_ADDRESS = URL_SERVER_IP_PORT + "/eat/defaultaddress";
  public static final String EAT_ORDER_SUCCESS = URL_SERVER_IP_PORT + "/eat/orderplace";
  public static final String EAT_MASTER = URL_SERVER_IP_PORT + "/masters";
  public static final String EAT_CUISINELIST = URL_SERVER_IP_PORT + "/masters/cuisinelist";
  public static final String EAT_CUSTOMER_SUPPORT = URL_SERVER_IP_PORT + "/eat/customersupport";
  public static final String EAT_X_FACTOR = URL_SERVER_IP_PORT + "/eat/getXfactors";
  public static final String EAT_REGION_LIST = URL_SERVER_IP_PORT + "/eat/regionlist";
  public static final String EAT_MASTER_REGION_LIST = URL_SERVER_IP_PORT + "/masters/regionlist";
  public static final String EAT_MASTER_HOMETOWN_LIST = URL_SERVER_IP_PORT + "/masters/homedownlist";
  public static final String EAT_REGION_KITCHEN_LIST = URL_SERVER_IP_PORT + "/eat/kitche/showmore";
  public static final String EAT_EXPLORE_SEARCH = URL_SERVER_IP_PORT + "/eat/quicksearch";
  public static final String EAT_STORIES_LIST = URL_SERVER_IP_PORT + "/eat/stories";
  public static final String EAT_COLLECTION_LIST = URL_SERVER_IP_PORT + "/eat/collection";
  public static final String EAT_COLLECTION_ICON_LIST = URL_SERVER_IP_PORT + "/eat/collectionicons";
  public static final String EAT_PAYMENT_RETRY_URL = URL_SERVER_IP_PORT + "/eat/payment/retry";

  public static final String EAT_PROMOTION_URL = URL_SERVER_IP_PORT + "/eat/promotion/homescreen";
  public static final String EAT_CHAT_ISSUES_URL = URL_SERVER_IP_PORT + "/eat/zendesk/issues";
  public static final String EAT_CHAT_ISSUES_NOTE_URL = URL_SERVER_IP_PORT + "/eat/zendesk/issuesdetails";
  public static final String EAT_CHAT_MAP_ORDERID = URL_SERVER_IP_PORT + "/eat/zendesk/requestcreate ";

  /////Daily locally
  public static final String FORCE_UPDATE = URL_SERVER_IP_PORT + "/user/app/versioncheck";
  public static final String SEND_OTP = URL_SERVER_IP_PORT + "/user/sendotp";
  public static final String OTP_VERIFICATION = URL_SERVER_IP_PORT + "/user/otpverification";
  public static final String REGISTRATION = URL_SERVER_IP_PORT + "/user/edit";



  public static final String DUNZO_URL = BuildConfig.DUNZO_URL;
  public static final String DUNZO_STATUS = BuildConfig.DUNZO_STATUS;


  //  public static final String EAT_REGION_LIST=  "http://192.168.1.102/tovo/regions.json";

  public static final int ADMIN = 0;
  public static final int RAZORPAY_TYPE = 2;
  public static final int MAKEIT = 1;

  //  public static final String EAT_REGION_LIST=  "http://192.168.1.102/tovo/regions.json";
  public static final int MOVEIT = 2;

  //public static final String DUNZO_URL = "https://api.dunzo.in/api/v1/tasks/";

  //    public static final String DUNZO_URL = "https://api.dunzo.in/api/v1/tasks/";
  public static final int SALES = 3;
  public static final int EAT = 4;
  public static final int MALE = 1;
  public static final int FEMALE = 2;
  public static final boolean FLAG_TRUE = true;
  public static final boolean FLAG_FALSE = false;
  ////ToAST
  public static final String TOAST_ENTER_REPLY_TO_SEND = "Enter reply to send";
  public static final String TOAST_ENTER_QUERY_TO_SEND = "Enter query to send";
  public static final String TOAST_ENTER_MOBILE_NO = "Enter mobile number";
  public static final String TOAST_ENTER_VALID_MOBILE_NO = "Enter valid mobile number";
  public static final String TOAST_ENTER_OTP = "Enter OTP";
  public static final String TOAST_ENTER_NAME = "Enter Name";
  public static final String TOAST_ENTER_EMAIL = "Enter Email";
  public static final String TOAST_ENTER_INVALID_EMAIL = "Invalid Email Address";
  public static final String TOAST_ENTER_PASSWORD = "Enter password";
  public static final String TOAST_ENTER_RE_ENTER_PASSWORD = "ReType password";
  public static final String TOAST_PASSWORD_NOT_MATCHING = "Password not matching";
  public static final String TOAST_LOGIN_SUCCESS = "Login success";
  public static final String TOAST_LOGIN_FAILED = "Login failed";
  public static final int TERMS_AND_CONDITION_REQUEST_CODE = 701;
  public static final String CANCEL_ORDER_MEAASAGE_1 = "I entered the wrong delivery address";
  public static final String CANCEL_ORDER_MEAASAGE_2 = "I added the wrong items";
  public static final String CANCEL_ORDER_MEAASAGE_3 = "Placed order by mistake";
  public static AppPreferencesHelper preferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
  //public static final String SUPPORT_NUMBER = "9790876528";
  public static final String SUPPORT_NUMBER = preferencesHelper.getSupportNumber();

  public AppConstants() {
    // This utility class is not publicly instantiable
  }

  public static Map<String, String> setHeaders(String version) {

    HashMap<String, String> headers = new HashMap<>();
    //headers.put("Content-Type", "application/json");
    //  headers.put("Content-Type", "application/json; charset=utf-8");
    headers.put("accept-version", version);
    headers.put("app-version", String.valueOf(BuildConfig.VERSION_CODE));
    headers.put("apptype", AppConstants.APP_TYPE_ANDROID);
    //  headers.put("Authorization","Bearer");
    AppPreferencesHelper preferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
    headers.put("Authorization", "Bearer " + preferencesHelper.getApiToken());
    // headers.put("token",preferencesHelper.getApiToken());
    return headers;
  }

  /**
   * METRICS
   */
  /////METRICS FOR APP OPENS
  public static final String METRICS_APP_OPENS = "met_ao";
  public static final String APP_OPENS_PREVIOUS_PAGE = "ao_prepg";
  public static final String APP_OPENS_SERVICEABLE_COUNT = "ao_servcount";
  public static final String APP_OPENS_UNSERVICEABLE_COUNT = "ao_unservcount";
  public static final String APP_OPENS_REGION_COUNT = "ao_regcount";
  public static final String APP_OPENS_ADDRESS_TYPE = "ao_addtag";
  public static final String APP_OPENS_SERVICEABLE_KITCHEN_LIST = "ao_servcid";
  public static final String APP_OPENS_UNSERVICEABLE_KITCHEN_LIST = "ao_unservcid";
  public static final String APP_OPENS_REGIONS_LIST = "ao_reglist";
  public static final String APP_OPENS_SERVICEABLESTATUS = "ao_servstatus";

  /////METRICS FOR APP HOME
  public static final String METRICS_APP_HOME = "met_home";
  public static final String APP_HOME_PREVIOUS_PAGE = "home_prepg";
  public static final String APP_HOME_SERVICEABLE_COUNT = "home_servcount";
  public static final String APP_HOME_UNSERVICEABLE_COUNT = "home_unservcount";
  public static final String APP_HOME_REGION_COUNT = "home_regcount";
  public static final String APP_HOME_ADDRESS_TYPE = "home_addtag";
  public static final String APP_HOME_SERVICEABLE_KITCHEN_LIST = "home_servcid";
  public static final String APP_HOME_UNSERVICEABLE_KITCHEN_LIST = "home_unservcid";
  public static final String APP_HOME_REGIONS_LIST = "home_reglist";
  public static final String APP_HOME_SCROLL = "home_scroll";
  public static final String APP_CATEGORY_COUNT = "home_categorycount";
  public static final String APP_COLLECRTION_COUNT = "home_collectioncount";
  public static final String APP_HOME_SERVICEABLESTATUS = "home_servstatus";
  /////METRICS FOR KITCHEN PAGE
  public static final String METRICS_KITCHEN_PAGE = "met_cp";
  public static final String KITCHEN_PAGE_MAKEIT_ID = "cp_cid";
  public static final String KITCHEN_PAGE_ETA = "cp_eta";
  public static final String KITCHEN_PAGE_RATING = "cp_rat";
  public static final String KITCHEN_PAGE_PRODUCT_FAVORITE_SECTION_COUNT = "cp_favp";
  public static final String KITCHEN_PAGE_PRODUCT_OTHER_COMBOS_SECTION_COUNT = "cp_comp";
  public static final String KITCHEN_PAGE_PRODUCT_OTHER_ITEMS_SECTION_COUNT = "cp_itemp";
  public static final String KITCHEN_PAGE_PRODUCT_OTHER_ITEMS_COMBO_TDYS_MENU_COUNT = "cp_favp_comp_itemp";
  public static final String KITCHEN_PAGE_NEXT_AVAILABLE_PRODUCT_COUNT = "cp_nap";
  public static final String KITCHEN_PAGE_SERVICEABILITY = "cp_serv";
  public static final String KITCHEN_PAGE_HOME_MAKER_BADGE = "cp_bdge";
  public static final String KITCHEN_PAGE_FAVORITE_BY_USER = "cp_favst";
  public static final String KITCHEN_PAGE_VEG_ONLY = "cp_vego";

  /////METRICS FOR REGION PAGE
  public static final String METRICS_REGION_PAGE = "met_rp";
  public static final String REGION_PREVIOUS_PAGE = "rp_previouspg";
  public static final String REGION_PAGE_REGION_ID = "rp_regid";
  public static final String REGION_PAGE_REGION_NAME = "rp_regname";
  public static final String REGION_PAGE_SERVICEABLE_COUNT = "rp_servcount";
  public static final String REGION_PAGE_UNSERVICEABLE_COUNT = "rp_unservcount";
  public static final String REGION_PAGE_SERVICEABLE_KITCHEN_LIST = "rp_serv_list";
  public static final String REGION_PAGE_UNSERVICEABLE_KITCHEN_LIST = "rp_unserv_list";

  /////METRICS FOR SEARCH EVENT
  public static final String METRICS_SEARCH = "met_sr";
  public static final String WORD_SEARCHED = "sr_wrd_find";
  public static final String SEARCH_PREVOIUS_PAGE = "sr_prevpg";
  public static final String REGION_SUGGESTION_COUNT = "sr_rg_sugc";
  public static final String KITCHEN_SUGGESTION_COUNT = "sr_kt_sugc";
  public static final String DISH_SUGGESTION_COUNT = "sr_prod_sug";
  public static final String TYPE = "sr_click_typ";
  public static final String UNIQUE_ID = "sr_click_uid";
  public static final String REGION_SUGGESTION_LIST = "sr_rg_sug_list";
  public static final String KITCHEN_SUGGESTION_LIST = "sr_cat_sug_list";
  public static final String DISH_SUGGESTION_LIST = "sr_prod_sug_list";
  public static final String SEARCH_CLICK_TYPE = "sr_click_typ";

  /////METRICS FOR ADD TO CART PAGE
  public static final String METRICS_ADD_TO_CART = "met_atc";
  public static final String ADD_TO_CART_PRODUCT_ID = "atc_pid";
  public static final String ADD_TO_CART_CURRENT_PAGE = "atc_currentpg";
  public static final String ADD_TO_CART_PRICE = "atc_pprice";
  public static final String ADD_TO_CART_QUANTITY = "atc_pqty";
  public static final String ADD_TO_CART_PRODUCT_TYPE = "atc_pcat";
  public static final String ADD_TO_CART_IS_FAVORITE_PRODUCT = "atc_isfavp";
  public static final String ADD_TO_CART_ACTION = "atc_action";

  /////METRICS FOR OPEN CART PAGE
  public static final String METRICS_OPEN_CART_PAGE = "met_cartopen";
  public static final String OPEN_CART_PAGE_MAKEIT_ID = "cart_cid";
  public static final String OPEN_CART_PREVIOUS_PAGE = "cart_prevpg";
  public static final String OPEN_CART_PAGE_TOTAL_AMOUNT = "cart_amt";
  public static final String OPEN_CART_PAGE_PROMO_CODE = "cart_promo";
  public static final String OPEN_CART_PAGE_DELIVERY_ADDRESS_TYPE = "cart_addtag";
  public static final String OPEN_CART_PRODUCT_ID_AND_QUANTITY_LIST = "cart_pid_qty_list";

  /////METRICS FOR PAYMENT METHOD PAGE
  public static final String METRICS_PAYMENT_METHOD_PAGE = "met_pay";
  public static final String PAYMENT_METHOD_PAGE_COD_OR_ONLINE = "pay_type";
  public static final String PAYMENT_METHOD_PREVIOUS_PAGE = "pay_prevpg";

  /////METRICS FOR TRACK ORDER PAGE
  public static final String METRICS_TRACK_ORDER_PAGE = "met_track";
  public static final String TRACK_ORDER_PAGE_ORDER_ID = "track_ordid";
  public static final String TRACK_ORDER_PREVIOUS_PAGE = "track_prevpg";

  /////METRICS FOR SEARCH SUGGESTION LIST
  public static final String METRICS_SEARCH_SUGGESTION = "met_srsug";
  public static final String SEARCH_SUGGESTION_WORD = "srsug_wrd_find";
  public static final String SEARCH_SUGGESTION_REGION_COUNT = "srsug_regc";
  public static final String SEARCH_SUGGESTION_REGION_LIST = "srsug_reg_list";
  public static final String SEARCH_SUGGESTION_KITCHEN_COUNT = "srsug_ktc";
  public static final String SEARCH_SUGGESTION_KITCHEN_LIST = "srsug_ktc_list";
  public static final String SEARCH_SUGGESTION_DISH_COUNT = "srsug_dishc";
  public static final String SEARCH_SUGGESTION_DISH_LIST = "srsug_dish_list";

  /////METRICS FOR PROCEED TO PAY
  public static final String METRICS_PROCEED_TO_PAY = "met_ptp";
  public static final String PROCEED_TO_PAY_KITCHEN_ID = "ptp_cid";
  public static final String PROCEED_TO_PAY_PRODUCT_ID_LIST = "ptp_pid_list";
  public static final String PROCEED_TO_PAY_PRODUCT_QUANTITY_LIST = "ptp_pqty";
  public static final String PROCEED_TO_PAY_AMOUNT = "ptp_amt";
  public static final String PROCEED_TO_PAY_PROMO_CODE = "ptp_promo";
  public static final String PROCEED_TO_PAY_REFUND_ID = "ptp_refid";
  public static final String PROCEED_TO_PAY_PREVIOUS_PAGE = "ptp_prepg";
  public static final String PROCEED_TO_PAY_MINCARTVALUE = "ptp_mincartvalue";

  /////METRICS ORDER RATING
  public static final String METRICS_ORDER_RATING = "met_order_feedback";
  public static final String PRODUCT_RATING = "prod_rating";
  public static final String DELIVERY_RATING = "del_rating";
  public static final String PRODUCT_FEEDBACK = "prod_feedback";
  public static final String DELIVERY_FEEDBACK = "del_feedback";

}
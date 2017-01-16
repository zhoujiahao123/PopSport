package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by ASUS-NB on 2017/1/15.
 */

public class WeatherInfo {
    /**
     * reason : successed!
     * result : {"data":{"pubdate":"2017-01-15","pubtime":"11:00:00","realtime":{"city_code":"101040100","city_name":"重庆","date":"2017-01-15","time":"14:00:00","week":0,"moon":"十二月十八","dataUptime":1484462103,"weather":{"temperature":"12","humidity":"56","info":"多云","img":"1"},"wind":{"direct":"西北风","power":"1级","offset":null,"windspeed":null}},"life":{"date":"2017-1-15","info":{"chuanyi":["较冷","建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"],"ganmao":["较易发","昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。"],"kongtiao":["较少开启","您将感到很舒适，一般不需要开启空调。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"yundong":["较适宜","阴天，较适宜进行各种户内外运动。"],"ziwaixian":["最弱","属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"]}},"weather":[{"date":"2017-01-15","info":{"day":["2","阴","12","","微风","07:49"],"night":["2","阴","7","","微风","18:17"]},"week":"日","nongli":"十二月十八"},{"date":"2017-01-16","info":{"dawn":["2","阴","7","无持续风向","微风","18:17"],"day":["7","小雨","9","","微风","07:49"],"night":["7","小雨","7","","微风","18:17"]},"week":"一","nongli":"十二月十九"},{"date":"2017-01-17","info":{"dawn":["7","小雨","7","无持续风向","微风","18:17"],"day":["7","小雨","9","","微风","07:49"],"night":["7","小雨","7","","微风","18:18"]},"week":"二","nongli":"十二月二十"},{"date":"2017-01-18","info":{"dawn":["7","小雨","7","无持续风向","微风","18:18"],"day":["2","阴","10","","微风","07:48"],"night":["2","阴","8","","微风","18:19"]},"week":"三","nongli":"十二月廿一"},{"date":"2017-01-19","info":{"dawn":["2","阴","8","无持续风向","微风","18:19"],"day":["2","阴","12","","微风","07:48"],"night":["2","阴","8","","微风","18:20"]},"week":"四","nongli":"十二月廿二"}],"f3h":{"temperature":[{"jg":"20170115140000","jb":"12"},{"jg":"20170115170000","jb":"10"},{"jg":"20170115200000","jb":"9"},{"jg":"20170115230000","jb":"8"},{"jg":"20170116020000","jb":"8"},{"jg":"20170116050000","jb":"8"},{"jg":"20170116080000","jb":"9"},{"jg":"20170116110000","jb":"8"},{"jg":"20170116140000","jb":"8"}],"precipitation":[{"jg":"20170115140000","jf":"0"},{"jg":"20170115170000","jf":"0"},{"jg":"20170115200000","jf":"0"},{"jg":"20170115230000","jf":"0"},{"jg":"20170116020000","jf":"0"},{"jg":"20170116050000","jf":"0"},{"jg":"20170116080000","jf":"0"},{"jg":"20170116110000","jf":"0.4"},{"jg":"20170116140000","jf":"0.4"}]},"pm25":{"key":"Chongqing","show_desc":0,"pm25":{"curPm":"81","pm25":"59","pm10":"82","level":2,"quality":"良","des":"可以正常在户外活动，易敏感人群应减少外出"},"dateTime":"2017年01月15日14时","cityName":"重庆"},"jingqu":"","jingqutq":"","date":"","isForeign":"0"}}
     * error_code : 0
     */

    private String reason;
    /**
     * data : {"pubdate":"2017-01-15","pubtime":"11:00:00","realtime":{"city_code":"101040100","city_name":"重庆","date":"2017-01-15","time":"14:00:00","week":0,"moon":"十二月十八","dataUptime":1484462103,"weather":{"temperature":"12","humidity":"56","info":"多云","img":"1"},"wind":{"direct":"西北风","power":"1级","offset":null,"windspeed":null}},"life":{"date":"2017-1-15","info":{"chuanyi":["较冷","建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"],"ganmao":["较易发","昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。"],"kongtiao":["较少开启","您将感到很舒适，一般不需要开启空调。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"yundong":["较适宜","阴天，较适宜进行各种户内外运动。"],"ziwaixian":["最弱","属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"]}},"weather":[{"date":"2017-01-15","info":{"day":["2","阴","12","","微风","07:49"],"night":["2","阴","7","","微风","18:17"]},"week":"日","nongli":"十二月十八"},{"date":"2017-01-16","info":{"dawn":["2","阴","7","无持续风向","微风","18:17"],"day":["7","小雨","9","","微风","07:49"],"night":["7","小雨","7","","微风","18:17"]},"week":"一","nongli":"十二月十九"},{"date":"2017-01-17","info":{"dawn":["7","小雨","7","无持续风向","微风","18:17"],"day":["7","小雨","9","","微风","07:49"],"night":["7","小雨","7","","微风","18:18"]},"week":"二","nongli":"十二月二十"},{"date":"2017-01-18","info":{"dawn":["7","小雨","7","无持续风向","微风","18:18"],"day":["2","阴","10","","微风","07:48"],"night":["2","阴","8","","微风","18:19"]},"week":"三","nongli":"十二月廿一"},{"date":"2017-01-19","info":{"dawn":["2","阴","8","无持续风向","微风","18:19"],"day":["2","阴","12","","微风","07:48"],"night":["2","阴","8","","微风","18:20"]},"week":"四","nongli":"十二月廿二"}],"f3h":{"temperature":[{"jg":"20170115140000","jb":"12"},{"jg":"20170115170000","jb":"10"},{"jg":"20170115200000","jb":"9"},{"jg":"20170115230000","jb":"8"},{"jg":"20170116020000","jb":"8"},{"jg":"20170116050000","jb":"8"},{"jg":"20170116080000","jb":"9"},{"jg":"20170116110000","jb":"8"},{"jg":"20170116140000","jb":"8"}],"precipitation":[{"jg":"20170115140000","jf":"0"},{"jg":"20170115170000","jf":"0"},{"jg":"20170115200000","jf":"0"},{"jg":"20170115230000","jf":"0"},{"jg":"20170116020000","jf":"0"},{"jg":"20170116050000","jf":"0"},{"jg":"20170116080000","jf":"0"},{"jg":"20170116110000","jf":"0.4"},{"jg":"20170116140000","jf":"0.4"}]},"pm25":{"key":"Chongqing","show_desc":0,"pm25":{"curPm":"81","pm25":"59","pm10":"82","level":2,"quality":"良","des":"可以正常在户外活动，易敏感人群应减少外出"},"dateTime":"2017年01月15日14时","cityName":"重庆"},"jingqu":"","jingqutq":"","date":"","isForeign":"0"}
     */

    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * pubdate : 2017-01-15
         * pubtime : 11:00:00
         * realtime : {"city_code":"101040100","city_name":"重庆","date":"2017-01-15","time":"14:00:00","week":0,"moon":"十二月十八","dataUptime":1484462103,"weather":{"temperature":"12","humidity":"56","info":"多云","img":"1"},"wind":{"direct":"西北风","power":"1级","offset":null,"windspeed":null}}
         * life : {"date":"2017-1-15","info":{"chuanyi":["较冷","建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"],"ganmao":["较易发","昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。"],"kongtiao":["较少开启","您将感到很舒适，一般不需要开启空调。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"yundong":["较适宜","阴天，较适宜进行各种户内外运动。"],"ziwaixian":["最弱","属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"]}}
         * weather : [{"date":"2017-01-15","info":{"day":["2","阴","12","","微风","07:49"],"night":["2","阴","7","","微风","18:17"]},"week":"日","nongli":"十二月十八"},{"date":"2017-01-16","info":{"dawn":["2","阴","7","无持续风向","微风","18:17"],"day":["7","小雨","9","","微风","07:49"],"night":["7","小雨","7","","微风","18:17"]},"week":"一","nongli":"十二月十九"},{"date":"2017-01-17","info":{"dawn":["7","小雨","7","无持续风向","微风","18:17"],"day":["7","小雨","9","","微风","07:49"],"night":["7","小雨","7","","微风","18:18"]},"week":"二","nongli":"十二月二十"},{"date":"2017-01-18","info":{"dawn":["7","小雨","7","无持续风向","微风","18:18"],"day":["2","阴","10","","微风","07:48"],"night":["2","阴","8","","微风","18:19"]},"week":"三","nongli":"十二月廿一"},{"date":"2017-01-19","info":{"dawn":["2","阴","8","无持续风向","微风","18:19"],"day":["2","阴","12","","微风","07:48"],"night":["2","阴","8","","微风","18:20"]},"week":"四","nongli":"十二月廿二"}]
         * f3h : {"temperature":[{"jg":"20170115140000","jb":"12"},{"jg":"20170115170000","jb":"10"},{"jg":"20170115200000","jb":"9"},{"jg":"20170115230000","jb":"8"},{"jg":"20170116020000","jb":"8"},{"jg":"20170116050000","jb":"8"},{"jg":"20170116080000","jb":"9"},{"jg":"20170116110000","jb":"8"},{"jg":"20170116140000","jb":"8"}],"precipitation":[{"jg":"20170115140000","jf":"0"},{"jg":"20170115170000","jf":"0"},{"jg":"20170115200000","jf":"0"},{"jg":"20170115230000","jf":"0"},{"jg":"20170116020000","jf":"0"},{"jg":"20170116050000","jf":"0"},{"jg":"20170116080000","jf":"0"},{"jg":"20170116110000","jf":"0.4"},{"jg":"20170116140000","jf":"0.4"}]}
         * pm25 : {"key":"Chongqing","show_desc":0,"pm25":{"curPm":"81","pm25":"59","pm10":"82","level":2,"quality":"良","des":"可以正常在户外活动，易敏感人群应减少外出"},"dateTime":"2017年01月15日14时","cityName":"重庆"}
         * jingqu :
         * jingqutq :
         * date :
         * isForeign : 0
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            private String pubdate;
            private String pubtime;
            /**
             * city_code : 101040100
             * city_name : 重庆
             * date : 2017-01-15
             * time : 14:00:00
             * week : 0
             * moon : 十二月十八
             * dataUptime : 1484462103
             * weather : {"temperature":"12","humidity":"56","info":"多云","img":"1"}
             * wind : {"direct":"西北风","power":"1级","offset":null,"windspeed":null}
             */

            private RealtimeBean realtime;
            /**
             * date : 2017-1-15
             * info : {"chuanyi":["较冷","建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"],"ganmao":["较易发","昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。"],"kongtiao":["较少开启","您将感到很舒适，一般不需要开启空调。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"yundong":["较适宜","阴天，较适宜进行各种户内外运动。"],"ziwaixian":["最弱","属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"]}
             */

            private LifeBean life;
            private F3hBean f3h;
            /**
             * key : Chongqing
             * show_desc : 0
             * pm25 : {"curPm":"81","pm25":"59","pm10":"82","level":2,"quality":"良","des":"可以正常在户外活动，易敏感人群应减少外出"}
             * dateTime : 2017年01月15日14时
             * cityName : 重庆
             */

            private Pm25Bean pm25;
            private String jingqu;
            private String jingqutq;
            private String date;
            private String isForeign;
            /**
             * date : 2017-01-15
             * info : {"day":["2","阴","12","","微风","07:49"],"night":["2","阴","7","","微风","18:17"]}
             * week : 日
             * nongli : 十二月十八
             */

            private List<WeatherBean> weather;

            public String getPubdate() {
                return pubdate;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }

            public String getPubtime() {
                return pubtime;
            }

            public void setPubtime(String pubtime) {
                this.pubtime = pubtime;
            }

            public RealtimeBean getRealtime() {
                return realtime;
            }

            public void setRealtime(RealtimeBean realtime) {
                this.realtime = realtime;
            }

            public LifeBean getLife() {
                return life;
            }

            public void setLife(LifeBean life) {
                this.life = life;
            }

            public F3hBean getF3h() {
                return f3h;
            }

            public void setF3h(F3hBean f3h) {
                this.f3h = f3h;
            }

            public Pm25Bean getPm25() {
                return pm25;
            }

            public void setPm25(Pm25Bean pm25) {
                this.pm25 = pm25;
            }

            public String getJingqu() {
                return jingqu;
            }

            public void setJingqu(String jingqu) {
                this.jingqu = jingqu;
            }

            public String getJingqutq() {
                return jingqutq;
            }

            public void setJingqutq(String jingqutq) {
                this.jingqutq = jingqutq;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getIsForeign() {
                return isForeign;
            }

            public void setIsForeign(String isForeign) {
                this.isForeign = isForeign;
            }

            public List<WeatherBean> getWeather() {
                return weather;
            }

            public void setWeather(List<WeatherBean> weather) {
                this.weather = weather;
            }

            public static class RealtimeBean {
                private String city_code;
                private String city_name;
                private String date;
                private String time;
                private int week;
                private String moon;
                private int dataUptime;
                /**
                 * temperature : 12
                 * humidity : 56
                 * info : 多云
                 * img : 1
                 */

                private WeatherBean weather;
                /**
                 * direct : 西北风
                 * power : 1级
                 * offset : null
                 * windspeed : null
                 */

                private WindBean wind;

                public String getCity_code() {
                    return city_code;
                }

                public void setCity_code(String city_code) {
                    this.city_code = city_code;
                }

                public String getCity_name() {
                    return city_name;
                }

                public void setCity_name(String city_name) {
                    this.city_name = city_name;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public int getWeek() {
                    return week;
                }

                public void setWeek(int week) {
                    this.week = week;
                }

                public String getMoon() {
                    return moon;
                }

                public void setMoon(String moon) {
                    this.moon = moon;
                }

                public int getDataUptime() {
                    return dataUptime;
                }

                public void setDataUptime(int dataUptime) {
                    this.dataUptime = dataUptime;
                }

                public WeatherBean getWeather() {
                    return weather;
                }

                public void setWeather(WeatherBean weather) {
                    this.weather = weather;
                }

                public WindBean getWind() {
                    return wind;
                }

                public void setWind(WindBean wind) {
                    this.wind = wind;
                }

                public static class WeatherBean {
                    private String temperature;
                    private String humidity;
                    private String info;
                    private String img;

                    public String getTemperature() {
                        return temperature;
                    }

                    public void setTemperature(String temperature) {
                        this.temperature = temperature;
                    }

                    public String getHumidity() {
                        return humidity;
                    }

                    public void setHumidity(String humidity) {
                        this.humidity = humidity;
                    }

                    public String getInfo() {
                        return info;
                    }

                    public void setInfo(String info) {
                        this.info = info;
                    }

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }
                }

                public static class WindBean {
                    private String direct;
                    private String power;
                    private Object offset;
                    private Object windspeed;

                    public String getDirect() {
                        return direct;
                    }

                    public void setDirect(String direct) {
                        this.direct = direct;
                    }

                    public String getPower() {
                        return power;
                    }

                    public void setPower(String power) {
                        this.power = power;
                    }

                    public Object getOffset() {
                        return offset;
                    }

                    public void setOffset(Object offset) {
                        this.offset = offset;
                    }

                    public Object getWindspeed() {
                        return windspeed;
                    }

                    public void setWindspeed(Object windspeed) {
                        this.windspeed = windspeed;
                    }
                }
            }

            public static class LifeBean {
                private String date;
                private InfoBean info;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public InfoBean getInfo() {
                    return info;
                }

                public void setInfo(InfoBean info) {
                    this.info = info;
                }

                public static class InfoBean {
                    private List<String> chuanyi;
                    private List<String> ganmao;
                    private List<String> kongtiao;
                    private List<String> xiche;
                    private List<String> yundong;
                    private List<String> ziwaixian;

                    public List<String> getChuanyi() {
                        return chuanyi;
                    }

                    public void setChuanyi(List<String> chuanyi) {
                        this.chuanyi = chuanyi;
                    }

                    public List<String> getGanmao() {
                        return ganmao;
                    }

                    public void setGanmao(List<String> ganmao) {
                        this.ganmao = ganmao;
                    }

                    public List<String> getKongtiao() {
                        return kongtiao;
                    }

                    public void setKongtiao(List<String> kongtiao) {
                        this.kongtiao = kongtiao;
                    }

                    public List<String> getXiche() {
                        return xiche;
                    }

                    public void setXiche(List<String> xiche) {
                        this.xiche = xiche;
                    }

                    public List<String> getYundong() {
                        return yundong;
                    }

                    public void setYundong(List<String> yundong) {
                        this.yundong = yundong;
                    }

                    public List<String> getZiwaixian() {
                        return ziwaixian;
                    }

                    public void setZiwaixian(List<String> ziwaixian) {
                        this.ziwaixian = ziwaixian;
                    }
                }
            }

            public static class F3hBean {
                /**
                 * jg : 20170115140000
                 * jb : 12
                 */

                private List<TemperatureBean> temperature;
                /**
                 * jg : 20170115140000
                 * jf : 0
                 */

                private List<PrecipitationBean> precipitation;

                public List<TemperatureBean> getTemperature() {
                    return temperature;
                }

                public void setTemperature(List<TemperatureBean> temperature) {
                    this.temperature = temperature;
                }

                public List<PrecipitationBean> getPrecipitation() {
                    return precipitation;
                }

                public void setPrecipitation(List<PrecipitationBean> precipitation) {
                    this.precipitation = precipitation;
                }

                public static class TemperatureBean {
                    private String jg;
                    private String jb;

                    public String getJg() {
                        return jg;
                    }

                    public void setJg(String jg) {
                        this.jg = jg;
                    }

                    public String getJb() {
                        return jb;
                    }

                    public void setJb(String jb) {
                        this.jb = jb;
                    }
                }

                public static class PrecipitationBean {
                    private String jg;
                    private String jf;

                    public String getJg() {
                        return jg;
                    }

                    public void setJg(String jg) {
                        this.jg = jg;
                    }

                    public String getJf() {
                        return jf;
                    }

                    public void setJf(String jf) {
                        this.jf = jf;
                    }
                }
            }

            public static class Pm25Bean {
                /**
                 * curPm : 81
                 * pm25 : 59
                 * pm10 : 82
                 * level : 2
                 * quality : 良
                 * des : 可以正常在户外活动，易敏感人群应减少外出
                 */

                private Pm25Bean1 pm25;
                private String dateTime;
                private String cityName;

                public Pm25Bean1 getPm25() {
                    return pm25;
                }

                public void setPm25(Pm25Bean1 pm25) {
                    this.pm25 = pm25;
                }

                public String getDateTime() {
                    return dateTime;
                }

                public void setDateTime(String dateTime) {
                    this.dateTime = dateTime;
                }

                public String getCityName() {
                    return cityName;
                }

                public void setCityName(String cityName) {
                    this.cityName = cityName;
                }

                public static class Pm25Bean1 {
                    private String pm25;
                    private String quality;
                    private String des;

                    public String getPm25() {
                        return pm25;
                    }

                    public void setPm25(String pm25) {
                        this.pm25 = pm25;
                    }

                    public String getQuality() {
                        return quality;
                    }

                    public void setQuality(String quality) {
                        this.quality = quality;
                    }

                    public String getDes() {
                        return des;
                    }

                    public void setDes(String des) {
                        this.des = des;
                    }
                }
            }

            public static class WeatherBean {
                private String date;
                private InfoBean info;
                private String week;
                private String nongli;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public InfoBean getInfo() {
                    return info;
                }

                public void setInfo(InfoBean info) {
                    this.info = info;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getNongli() {
                    return nongli;
                }

                public void setNongli(String nongli) {
                    this.nongli = nongli;
                }

                public static class InfoBean {
                    private List<String> day;
                    private List<String> night;

                    public List<String> getDay() {
                        return day;
                    }

                    public void setDay(List<String> day) {
                        this.day = day;
                    }

                    public List<String> getNight() {
                        return night;
                    }

                    public void setNight(List<String> night) {
                        this.night = night;
                    }
                }
            }
        }
    }
}

package com.example.stevengo.myapplication.entitys;

import java.util.List;

/**
 * Created by StevenGo on 2017/10/19.
 */

public class LocationInfo {

    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * regeocode : {"formatted_address":"天津市滨海新区新港街道中共滨海新区纪律检查委员会","addressComponent":{"country":"中国","province":"天津市","city":[],"citycode":"022","district":"滨海新区","adcode":"120116","township":"新港街道","towncode":"120116003000","neighborhood":{"name":"侨雅新寓","type":"商务住宅;住宅区;住宅小区"},"building":{"name":"中共滨海新区纪律检查委员会","type":"政府机构及社会团体;政府机关;区县级政府及事业单位"},"streetNumber":{"street":"新港二号路","number":"2320号","location":"117.71051,39.0033978","direction":"南","distance":"29.1838"},"businessAreas":[{"location":"117.7104443773585,39.0037517253669","name":"新港","id":"120116"}]}}
     */

    private String status;
    private String info;
    private String infocode;
    private RegeocodeBean regeocode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public RegeocodeBean getRegeocode() {
        return regeocode;
    }

    public void setRegeocode(RegeocodeBean regeocode) {
        this.regeocode = regeocode;
    }

    public static class RegeocodeBean {
        /**
         * formatted_address : 天津市滨海新区新港街道中共滨海新区纪律检查委员会
         * addressComponent : {"country":"中国","province":"天津市","city":[],"citycode":"022","district":"滨海新区","adcode":"120116","township":"新港街道","towncode":"120116003000","neighborhood":{"name":"侨雅新寓","type":"商务住宅;住宅区;住宅小区"},"building":{"name":"中共滨海新区纪律检查委员会","type":"政府机构及社会团体;政府机关;区县级政府及事业单位"},"streetNumber":{"street":"新港二号路","number":"2320号","location":"117.71051,39.0033978","direction":"南","distance":"29.1838"},"businessAreas":[{"location":"117.7104443773585,39.0037517253669","name":"新港","id":"120116"}]}
         */

        private String formatted_address;
        private AddressComponentBean addressComponent;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public AddressComponentBean getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponentBean addressComponent) {
            this.addressComponent = addressComponent;
        }

        public static class AddressComponentBean {
            /**
             * country : 中国
             * province : 天津市
             * city : []
             * citycode : 022
             * district : 滨海新区
             * adcode : 120116
             * township : 新港街道
             * towncode : 120116003000
             * neighborhood : {"name":"侨雅新寓","type":"商务住宅;住宅区;住宅小区"}
             * building : {"name":"中共滨海新区纪律检查委员会","type":"政府机构及社会团体;政府机关;区县级政府及事业单位"}
             * streetNumber : {"street":"新港二号路","number":"2320号","location":"117.71051,39.0033978","direction":"南","distance":"29.1838"}
             * businessAreas : [{"location":"117.7104443773585,39.0037517253669","name":"新港","id":"120116"}]
             */

            private String country;
            private String province;
            private String citycode;
            private String district;
            private String adcode;
            private String township;
            private String towncode;
            private NeighborhoodBean neighborhood;
            private BuildingBean building;
            private StreetNumberBean streetNumber;
            private List<?> city;
            private List<BusinessAreasBean> businessAreas;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCitycode() {
                return citycode;
            }

            public void setCitycode(String citycode) {
                this.citycode = citycode;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getTownship() {
                return township;
            }

            public void setTownship(String township) {
                this.township = township;
            }

            public String getTowncode() {
                return towncode;
            }

            public void setTowncode(String towncode) {
                this.towncode = towncode;
            }

            public NeighborhoodBean getNeighborhood() {
                return neighborhood;
            }

            public void setNeighborhood(NeighborhoodBean neighborhood) {
                this.neighborhood = neighborhood;
            }

            public BuildingBean getBuilding() {
                return building;
            }

            public void setBuilding(BuildingBean building) {
                this.building = building;
            }

            public StreetNumberBean getStreetNumber() {
                return streetNumber;
            }

            public void setStreetNumber(StreetNumberBean streetNumber) {
                this.streetNumber = streetNumber;
            }

            public List<?> getCity() {
                return city;
            }

            public void setCity(List<?> city) {
                this.city = city;
            }

            public List<BusinessAreasBean> getBusinessAreas() {
                return businessAreas;
            }

            public void setBusinessAreas(List<BusinessAreasBean> businessAreas) {
                this.businessAreas = businessAreas;
            }

            public static class NeighborhoodBean {
                /**
                 * name : 侨雅新寓
                 * type : 商务住宅;住宅区;住宅小区
                 */

                private String name;
                private String type;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }

            public static class BuildingBean {
                /**
                 * name : 中共滨海新区纪律检查委员会
                 * type : 政府机构及社会团体;政府机关;区县级政府及事业单位
                 */

                private String name;
                private String type;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }

            public static class StreetNumberBean {
                /**
                 * street : 新港二号路
                 * number : 2320号
                 * location : 117.71051,39.0033978
                 * direction : 南
                 * distance : 29.1838
                 */

                private String street;
                private String number;
                private String location;
                private String direction;
                private String distance;

                public String getStreet() {
                    return street;
                }

                public void setStreet(String street) {
                    this.street = street;
                }

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public String getDirection() {
                    return direction;
                }

                public void setDirection(String direction) {
                    this.direction = direction;
                }

                public String getDistance() {
                    return distance;
                }

                public void setDistance(String distance) {
                    this.distance = distance;
                }
            }

            public static class BusinessAreasBean {
                /**
                 * location : 117.7104443773585,39.0037517253669
                 * name : 新港
                 * id : 120116
                 */

                private String location;
                private String name;
                private String id;

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }
    }
}

package com.example.stevengo.myapplication.entitys;

import java.util.List;

/**
 * Created by StevenGo on 2017/9/30.
 * 音乐的实体，封装音乐的信息
 */

public class MusicEntity {

    /**
     * album_offset : 0
     * albums : [{"artists":[{"id":61766614,"name":"刘子维","portrait":"http://pic.cdn.duomi.com/imageproxy2/dimgm/scaleImage?url=http://img.kxting.cn//p1/28/10/72148405.jpg&w=150&h=150&s=100&c=0&o=0&m=","valid":true}],"available":true,"company":"","cover":"http://pic.cdn.duomi.com/imageproxy2/dimgm/scaleImage?url=http://img.kxting.cn//p1/19/18/72147621.jpg&w=150&h=150&s=100&c=0&o=0&m=","id":2694020,"name":"龙韵养生二胡曲典(二)","num_tracks":8,"release_date":"2016-01-19","type":"专辑"},{"artists":[{"id":61766614,"name":"刘子维","portrait":"http://pic.cdn.duomi.com/imageproxy2/dimgm/scaleImage?url=http://img.kxting.cn//p1/28/10/72148405.jpg&w=150&h=150&s=100&c=0&o=0&m=","valid":true}],"available":true,"company":"","cover":"http://pic.cdn.duomi.com/imageproxy2/dimgm/scaleImage?url=http://img.kxting.cn//p1/20/19/72147622.jpg&w=150&h=150&s=100&c=0&o=0&m=","id":2694021,"name":"龙韵养生二胡曲典(一)","num_tracks":8,"release_date":"2016-01-19","type":"专辑"}]
     * artist_offset : 0
     * artists : [{"id":61766614,"name":"刘子维","num_albums":25,"num_tracks":34,"portrait":"http://pic.cdn.duomi.com/imageproxy2/dimgm/scaleImage?url=http://img.kxting.cn//p1/28/10/72148405.jpg&w=150&h=150&s=100&c=0&o=0&m=","valid":true}]
     * dm_error : 0
     * error_msg : 操作成功
     * recommend : 0
     * total_albums : 2
     * total_artists : 1
     * total_tracks : 25
     * track_offset : 0
     * tracks : [{"album":{"cover":"/p1/17/25/72289165.jpg","id":2711779,"name":"杨梅之恋"},"artists":[{"id":61766614,"name":"刘子维","num_albums":25,"num_tracks":34,"portrait":"/p1/28/10/72148405.jpg","valid":true}],"availability":"1110","dlyric":"","id":27879052,"medias":[{"bitrate":320,"p2purl":"0EBA8184010BE211DF05000000D62E643400000095.mp3"}],"mv":0,"slyric":"static/22/02/93311231.txt","title":"杨梅之恋(二胡版)","isdown":"1","isplay":"1"}]
     */

    private int album_offset;
    private int artist_offset;
    private int dm_error;
    private String error_msg;
    private int recommend;
    private int total_albums;
    private int total_artists;
    private int total_tracks;
    private int track_offset;
    private List<AlbumsBean> albums;
    private List<ArtistsBeanX> artists;
    private List<TracksBean> tracks;

    public int getAlbum_offset() {
        return album_offset;
    }

    public void setAlbum_offset(int album_offset) {
        this.album_offset = album_offset;
    }

    public int getArtist_offset() {
        return artist_offset;
    }

    public void setArtist_offset(int artist_offset) {
        this.artist_offset = artist_offset;
    }

    public int getDm_error() {
        return dm_error;
    }

    public void setDm_error(int dm_error) {
        this.dm_error = dm_error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public int getTotal_albums() {
        return total_albums;
    }

    public void setTotal_albums(int total_albums) {
        this.total_albums = total_albums;
    }

    public int getTotal_artists() {
        return total_artists;
    }

    public void setTotal_artists(int total_artists) {
        this.total_artists = total_artists;
    }

    public int getTotal_tracks() {
        return total_tracks;
    }

    public void setTotal_tracks(int total_tracks) {
        this.total_tracks = total_tracks;
    }

    public int getTrack_offset() {
        return track_offset;
    }

    public void setTrack_offset(int track_offset) {
        this.track_offset = track_offset;
    }

    public List<AlbumsBean> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumsBean> albums) {
        this.albums = albums;
    }

    public List<ArtistsBeanX> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistsBeanX> artists) {
        this.artists = artists;
    }

    public List<TracksBean> getTracks() {
        return tracks;
    }

    public void setTracks(List<TracksBean> tracks) {
        this.tracks = tracks;
    }

    public static class AlbumsBean {
        /**
         * artists : [{"id":61766614,"name":"刘子维","portrait":"http://pic.cdn.duomi.com/imageproxy2/dimgm/scaleImage?url=http://img.kxting.cn//p1/28/10/72148405.jpg&w=150&h=150&s=100&c=0&o=0&m=","valid":true}]
         * available : true
         * company :
         * cover : http://pic.cdn.duomi.com/imageproxy2/dimgm/scaleImage?url=http://img.kxting.cn//p1/19/18/72147621.jpg&w=150&h=150&s=100&c=0&o=0&m=
         * id : 2694020
         * name : 龙韵养生二胡曲典(二)
         * num_tracks : 8
         * release_date : 2016-01-19
         * type : 专辑
         */

        private boolean available;
        private String company;
        private String cover;
        private int id;
        private String name;
        private int num_tracks;
        private String release_date;
        private String type;
        private List<ArtistsBean> artists;

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum_tracks() {
            return num_tracks;
        }

        public void setNum_tracks(int num_tracks) {
            this.num_tracks = num_tracks;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ArtistsBean> getArtists() {
            return artists;
        }

        public void setArtists(List<ArtistsBean> artists) {
            this.artists = artists;
        }

        public static class ArtistsBean {
            /**
             * id : 61766614
             * name : 刘子维
             * portrait : http://pic.cdn.duomi.com/imageproxy2/dimgm/scaleImage?url=http://img.kxting.cn//p1/28/10/72148405.jpg&w=150&h=150&s=100&c=0&o=0&m=
             * valid : true
             */

            private int id;
            private String name;
            private String portrait;
            private boolean valid;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public boolean isValid() {
                return valid;
            }

            public void setValid(boolean valid) {
                this.valid = valid;
            }
        }
    }

    public static class ArtistsBeanX {
        /**
         * id : 61766614
         * name : 刘子维
         * num_albums : 25
         * num_tracks : 34
         * portrait : http://pic.cdn.duomi.com/imageproxy2/dimgm/scaleImage?url=http://img.kxting.cn//p1/28/10/72148405.jpg&w=150&h=150&s=100&c=0&o=0&m=
         * valid : true
         */

        private int id;
        private String name;
        private int num_albums;
        private int num_tracks;
        private String portrait;
        private boolean valid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum_albums() {
            return num_albums;
        }

        public void setNum_albums(int num_albums) {
            this.num_albums = num_albums;
        }

        public int getNum_tracks() {
            return num_tracks;
        }

        public void setNum_tracks(int num_tracks) {
            this.num_tracks = num_tracks;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }
    }

    public static class TracksBean {
        /**
         * album : {"cover":"/p1/17/25/72289165.jpg","id":2711779,"name":"杨梅之恋"}
         * artists : [{"id":61766614,"name":"刘子维","num_albums":25,"num_tracks":34,"portrait":"/p1/28/10/72148405.jpg","valid":true}]
         * availability : 1110
         * dlyric :
         * id : 27879052
         * medias : [{"bitrate":320,"p2purl":"0EBA8184010BE211DF05000000D62E643400000095.mp3"}]
         * mv : 0
         * slyric : static/22/02/93311231.txt
         * title : 杨梅之恋(二胡版)
         * isdown : 1
         * isplay : 1
         */

        private AlbumBean album;
        private String availability;
        private String dlyric;
        private int id;
        private int mv;
        private String slyric;
        private String title;
        private String isdown;
        private String isplay;
        private List<ArtistsBeanXX> artists;
        private List<MediasBean> medias;

        public AlbumBean getAlbum() {
            return album;
        }

        public void setAlbum(AlbumBean album) {
            this.album = album;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public String getDlyric() {
            return dlyric;
        }

        public void setDlyric(String dlyric) {
            this.dlyric = dlyric;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMv() {
            return mv;
        }

        public void setMv(int mv) {
            this.mv = mv;
        }

        public String getSlyric() {
            return slyric;
        }

        public void setSlyric(String slyric) {
            this.slyric = slyric;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIsdown() {
            return isdown;
        }

        public void setIsdown(String isdown) {
            this.isdown = isdown;
        }

        public String getIsplay() {
            return isplay;
        }

        public void setIsplay(String isplay) {
            this.isplay = isplay;
        }

        public List<ArtistsBeanXX> getArtists() {
            return artists;
        }

        public void setArtists(List<ArtistsBeanXX> artists) {
            this.artists = artists;
        }

        public List<MediasBean> getMedias() {
            return medias;
        }

        public void setMedias(List<MediasBean> medias) {
            this.medias = medias;
        }

        public static class AlbumBean {
            /**
             * cover : /p1/17/25/72289165.jpg
             * id : 2711779
             * name : 杨梅之恋
             */

            private String cover;
            private int id;
            private String name;

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ArtistsBeanXX {
            /**
             * id : 61766614
             * name : 刘子维
             * num_albums : 25
             * num_tracks : 34
             * portrait : /p1/28/10/72148405.jpg
             * valid : true
             */

            private int id;
            private String name;
            private int num_albums;
            private int num_tracks;
            private String portrait;
            private boolean valid;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getNum_albums() {
                return num_albums;
            }

            public void setNum_albums(int num_albums) {
                this.num_albums = num_albums;
            }

            public int getNum_tracks() {
                return num_tracks;
            }

            public void setNum_tracks(int num_tracks) {
                this.num_tracks = num_tracks;
            }

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public boolean isValid() {
                return valid;
            }

            public void setValid(boolean valid) {
                this.valid = valid;
            }
        }

        public static class MediasBean {
            /**
             * bitrate : 320
             * p2purl : 0EBA8184010BE211DF05000000D62E643400000095.mp3
             */

            private int bitrate;
            private String p2purl;

            public int getBitrate() {
                return bitrate;
            }

            public void setBitrate(int bitrate) {
                this.bitrate = bitrate;
            }

            public String getP2purl() {
                return p2purl;
            }

            public void setP2purl(String p2purl) {
                this.p2purl = p2purl;
            }
        }
    }
}

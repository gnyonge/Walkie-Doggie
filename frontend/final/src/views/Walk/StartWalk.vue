<template>
<div>
  <div id="app">
    <div id="map"></div>
    <div>
    <!-- 좋아요 -->
    <v-btn
      elevation="2"
      large
      @click="like">좋아요</v-btn>
    <!-- 싫어요 -->
    <v-btn
      elevation="2"
      large
      @click="hate">싫어요</v-btn>
    <!-- 산책 종료  -->  
    <v-dialog
        transition="dialog-bottom-transition"
        max-width="600"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="primary"
            v-bind="attrs"
            v-on="on"
            @click="doneWalk">산책 종료</v-btn>
        </template>
        <template>
          <v-card>
            <v-toolbar
              color="primary"
              dark
            >뿌꾸 산책을 종료 합니다.(color값 조정해야함)</v-toolbar>
            <v-card-text>
              <div class="pa-12">
                산책 시작 시간 : <br>
                산책 종료 시간 : <br>
                산책 거리 : <br>
                좋아요 수 : <br>
                싫어요 수 : <br>
                </div>
            </v-card-text>
            <v-card-actions class="justify-end">
              <v-btn
                text
                @click="gotoMain"
              >창 닫기</v-btn>
            </v-card-actions>
          </v-card>
        </template>
      </v-dialog>
    </div>
  </div>
  </div>
</template>

<script>
// var map = new kakao.maps.Map(container, options);


export default {
  name: 'StartWalk',
  data() {
    return {
      mapContainer: '',
      mapOption: '',
      map : {},
      // emotion: Boolean,
    }
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement('script');
      // global kakao
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=6985aa694c9e9631fa03de6f22217f30';
      document.head.appendChild(script);
    }
  },
  methods: {
    // 지도 첫 화면 로드 
    initMap() {
      this.mapContainer = document.getElementById('map');
      this.mapOption = {
        //한강 주소 
        center: new kakao.maps.LatLng(37.507781796841705, 126.99274641516297),
        level: 5
      };

    this.map = new kakao.maps.Map(this.mapContainer, this.mapOption); // 지도를 생성합니다

    // HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
    if (navigator.geolocation) {
      // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      navigator.geolocation.getCurrentPosition(function(position) { 
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
        
        var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
            message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다

        // 마커와 인포윈도우를 표시합니다
        displayMarker(locPosition, message);
        });
    } else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
        var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),    
            message = 'geolocation을 사용할수 없어요..'
      
        displayMarker(locPosition, message);
    }
   
    var map = this.map
    // 지도에 마커와 인포윈도우를 표시하는 함수입니다
    function displayMarker(locPosition, message) {
      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({  
          map: map, 
          position: locPosition
      }); 

      var iwContent = message, // 인포윈도우에 표시할 내용
          iwRemoveable = true;

      // 인포윈도우를 생성합니다
      var infowindow = new kakao.maps.InfoWindow({
          content : iwContent,
          removable : iwRemoveable
      });
        
      // 인포윈도우를 마커위에 표시합니다 
      infowindow.open(map, marker);
      
      // 지도 중심좌표를 접속위치로 변경합니다
      map.setCenter(locPosition);   
    }   
    
  },
    
    // 좋아요
    like() {
      var dogLike = this.map
      // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      navigator.geolocation.getCurrentPosition(function(position) { 
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
        var markerPosition  = new kakao.maps.LatLng(lat, lon)
        console.log(`like에서 markerPosition${markerPosition}`)
      
      var imageSrc = 'https://i.ibb.co/X2SKnKb/like.png', // 마커이미지의 주소입니다    
          imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
          imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
      
      // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption)

      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
          position: markerPosition,
          image: markerImage // 마커이미지 설정 
        })
        
        // 마커가 지도 위에 표시되도록 설정합니다
      marker.setMap(dogLike);
      });
    },

    // 싫어요
    hate() {
      var dogHate = this.map
     // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      navigator.geolocation.getCurrentPosition(function(position) { 
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
        var markerPosition  = new kakao.maps.LatLng(lat, lon)
       
      var imageSrc = 'https://i.ibb.co/wzx63zW/hate.png', // 마커이미지의 주소입니다    
          imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
          imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
      
      // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption)
      
      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
          position: markerPosition,
          image: markerImage // 마커이미지 설정 
        })
      // like, hate 중복 처리 필요 
      // console.log(dogHate.getCenter())    
      // console.log(dogHate)    
      // 마커가 지도 위에 표시되도록 설정합니다
      marker.setMap(dogHate);
      });

    },

    // 산책종료
    doneWalk() {
      console.log("정보가져오기")
      
    },

    // 메인으로 보내기 
    gotoMain(){
      this.$router.push('/')
      // 하단바 색상 변경 
    }
  },
}
</script>

<style>
#map {
    width: 375px;
    height: 300px;
}
</style>
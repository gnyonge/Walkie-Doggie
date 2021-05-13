<template>
  <div>
    <div id="mainBox" style="margin-top: 10%;">
      <div id="app">
        <!-- 지도 -->
        <div id="map"></div>
      </div>
        <div class="btncalss">
          <!-- 좋아요 -->
          <v-btn
            large
            id="mainBtn"
            style="width: 10px;"
            @click="like">
           <v-icon style="color: #FC6C8C;">mdi-cards-heart</v-icon> 
          </v-btn>
          <!-- 멍플레이스 -->
          <v-btn
            large
            id="mainBtn"
            style="width: 10px; "
            @click="goToHotPlace">
            <v-icon style="color: #FA3C5A;">mdi-fire</v-icon>
            </v-btn>
          <!-- 산책 종료  -->  
          <v-dialog
              transition="dialog-bottom-transition"
              max-width="600">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              large
              v-bind="attrs"
              v-on="on"
              id="mainBtn"
              style="width: 10px; "
              @click="doneWalk">
              <v-icon style="color: #F2B75B">mdi-file-excel-box-outline</v-icon>
            </v-btn>
          </template>
          <template>
            <v-card>
              <v-card-text>
                <div style="text-align: center;">
                  <p><v-icon style="font-size: 120px;color: #7CE793;">mdi-check</v-icon></p>
                  <p>산책 시작 시간 :<br> {{ start }} </p>
                  <p>산책 종료 시간 :<br> {{ end }} </p>
                  <p>총 산책 시간 : {{ totalH }}시간 {{ totalM}}분</p>
                  <p>좋아요 수 : {{ likecnt }}</p>
                </div>
              </v-card-text>
                <v-card-actions class="justify-center">
                  <v-btn
                    text
                    id="mainBtn"
                    style="background-color: #FC6C8C;"
                    @click="gotoMain">
                    창 닫기
                  </v-btn>
                </v-card-actions>
              </v-card>
            </template>
          </v-dialog>
        </div>
      
    </div>
    <div class="d-flex justify-center img">
      <v-img
        height="80"
        class="rounded-circle gird"
        max-height="80"
        max-width="80"
        src="../../assets/dog.jpg">
      </v-img>
    </div>
  </div>
</template>


<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'

export default {
  name: 'StartWalk',
  data() {
    return {
      // 지도 
      mapContainer: '',
      mapOption: '',
      map: {},
      // 시간 
      start: '', 
      end: '', 
      // 총 시간
      beforeH: 0,
      beforeM:0,
      afterH:0,
      afterM: 0,
      totalH: 0,
      totalM: 0, 
      // 선호도 
      likecnt: 0,
      // 실시간 위치 
      walkLoc : '',
      linePath: [], 
      mylike: [],
      // 멍플레이스
      likePath: [],
      
    }
  },
  computed: {
    ...mapGetters(['getNowTab', 'getMyPath', 'getFirstAreaName', 'getAreaName'])
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement('script');
      // global kakao
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = 'https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=6985aa694c9e9631fa03de6f22217f30';
      document.head.appendChild(script);
    }

    // 시작 시간 가져오기 
    this.startTime() 
    
    // 실시간 위치 정보 
    this.navigation()

  },
  destroyed() {
    this.setMyPath(Array)
    clearInterval(this.walkLoc)
    
  },
  methods: {
    ...mapMutations(['setNowTab', 'setNowLon', 'setNowLat','setMyPath','setFirstAreaName','setAreaName' ]), 
    ...mapActions(['doneWalkInApi']),
    // 지도 첫 화면 로드 
    initMap() {
      this.mapContainer = document.getElementById('map');
      this.mapOption = {
        //한강 주소 
        center: new kakao.maps.LatLng(37.507781796841705, 126.99274641516297),
        draggable: true,
        level: 5
      };

    this.map = new kakao.maps.Map(this.mapContainer, this.mapOption); // 지도를 생성합니다
    
    // HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
    if (navigator.geolocation) {
      
      // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      navigator.geolocation.getCurrentPosition((position)=> { 
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
        
        // 첫위치 위도 -> 주소 
        this.getAddress(lon, lat)
        console.log('init')

        // 다시 들어올 떄마다 경로 받기 
        this.linePath = this.getMyPath
        this.linePath.push(new kakao.maps.LatLng(lat, lon))
        
        var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
            message = '<div style="padding:5px;">산책 시작.</div>'; // 인포윈도우에 표시될 내용입니다
        
       // 마커와 인포윈도우를 표시합니다
        displayMarker(locPosition, message);
  
      },function(error) {
      console.error(error);
    }, {
      maximumAge: 0,
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

      // message창 3초 후에 종료 
      setTimeout(function(){ 
        infowindow.close()
        marker.setMap(null);}, 3000)
      
      // 지도 중심좌표를 접속위치로 변경합니다
      map.setCenter(locPosition);  
      }  
    },
    // 위치 -> 주소 
    getAddress(lon, lat){
      console.log('getAdrress내부')

      const callback =  (result, status) => {
        console.log(this.getFirstAreaName)
        if (status === kakao.maps.services.Status.OK) {
          var detail = result[0].address.address_name
          console.log(detail)
          console.log('detail아래')
          if (this.getFirstAreaName === '') {
            console.log(detail, '123213')
            console.log('첫 장소 받기 성공')
            this.setFirstAreaName(detail)
          }else {
            console.log('첫 장소 아님')
            this.setAreaName(detail)
          }
        } 
      }
      //주소-좌표 변환 객체 생성 
      var geocoder = new kakao.maps.services.Geocoder();
      function searchDetailAddrFromCoords(lon, lat, callback) {
        // 좌표로 법정동 상세 주소 정보를 요청합니다
        geocoder.coord2Address(lon, lat, callback);
      }
      searchDetailAddrFromCoords(lon, lat, callback)
    },
    
    // 시간 가져오기 
    getTime() {
      let today = new Date() 
      let date = today.getFullYear() + '년' + (today.getMonth() + 1 ) + '월' + today.getDate() + '일'
      let time = today.getHours() + '시' + today.getMinutes() + '분'
      this.afterH = today.getHours()
      this.afterM = today.getMinutes()
      let dateTime = date + ' ' + time 
      return dateTime
    }, 
    // 시작 시간 가져오기 
    startTime() {
      let today = new Date() 
      let date = today.getFullYear() + '년' + (today.getMonth() + 1 ) + '월' + today.getDate() + '일'
      let time = today.getHours() + '시' + today.getMinutes() + '분'
      this.beforeH = today.getHours()
      this.beforeM = today.getMinutes()
      let dateTime = date + ' ' + time
      this.start = dateTime
    }, 
    // 산책시간 계산 
    calTime(){
      this.totalH = this.afterH - this.beforeH
      this.totalM = this.afterM - this.beforeM
    },
    // 좋아요
    like() {
      this.likecnt += 1 
      var t = this

      // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      navigator.geolocation.getCurrentPosition(function(position) { 
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
        
        t.setNowLat(lat) 
        t.setNowLon(lon)
        // 좋아요 포스팅  
        t.$router.push('/likeposting')
      })
    },
    // 나의 좋아요에 핀 꽂기 
    myLikePoint(){
      
    },
    // 산책종료
    doneWalk() {
      console.log(this.getFirstAreaName, '281')
      // 백엔드로 정보 보내기 
      this.doneWalkInApi({
        peid: "petpetpet1",
        w_date: this.start, 
        w_distance: "1.2",
        w_flag: 0,
        w_like: this.likecnt,
        w_time: (this.totalH * 60) + this.totalM,
        wid: 0,
        p_location: this.startAddress,
      }).then(()=> {
        // 실시간 정보 가져오기죽이기 
        clearInterval(this.walkLoc)
        this.end = this.getTime()
        this.calTime()
        // 저장되어 있던 정보도 지우기 
        this.setMyPath(Array)
        this.setNowLon(0)
        this.setNowLat(0)
      }).catch((error)=> {
        console.log(error)
      })
      
    },

    // 멍플레이스 
    goToHotPlace() {
    },
    
    // 메인으로 보내기 
    gotoMain(){
      // 하단바 색상 변경 
      this.setNowTab(0)
      this.$router.push('/calendar')
    },

    // 움직이는 경로 표시하기 
    navigation(){
      this.walkLoc = setInterval(this.getLocation, 10000)
    },
    
    // 위치 정보 기반 선 표시 
    getLocation() {
      var map = this.map
      var linePath = this.linePath
      var t = this 

      // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      navigator.geolocation.getCurrentPosition(function(position) { 
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
      linePath.push(new kakao.maps.LatLng(lat, lon))

      // 실시간 위치 정보 vuex로 보내기 
      t.setMyPath(new kakao.maps.LatLng(lat, lon))

      // 선 연결 
      var polyline = new kakao.maps.Polyline({
        path: linePath, // 선을 구성하는 좌표배열 
        strokeWeight: 5, // 선의 두께
        strokeColor: '#1E90FF', // 선의 색깔 
        strokeOpacity: 0.2, // 선의 불투명도, 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: 'solid' // 선의 스타일입니다
      })
      polyline.setMap(map)
      
      })
    }
  },
}
</script>

<style>
#map {
    width: auto;
    height: 480px;
}
.img{
  position: absolute;
  margin: 10% 40%;
  z-index: 1;
}
.gird{
  border: 5px solid rgb(250, 250, 250);

}
.btncalss{
  position: absolute;
  text-align: center;
  display: inline-block;
  margin: 2% 0%; 
  width: 295px;
  display: flex;
  justify-content: space-between;
  z-index: 1;
}

</style>
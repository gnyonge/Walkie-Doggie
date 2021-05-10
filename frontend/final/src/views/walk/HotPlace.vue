<template>
  <div>
    <div id="app">
      <!-- 지도 -->
      <div id="map"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HotPlace',
  data(){
    return{
      positions: [],
    }
  },
  mounted() {
    // 백엔드 서버에서 멍플레이스 정보 요청(axios)
    this.getPlace()
    // 카카오 map 
    if (window.kakao && window.kakao.maps) {
    this.initMap();
    } else {
    const script = document.createElement('script');
    // global kakao
    script.onload = () => kakao.maps.load(this.initMap);
    script.src = 'https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=6985aa694c9e9631fa03de6f22217f30';
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
        draggable: true,
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
            message = '<div style="padding:5px;">현재 위치.</div>'; // 인포윈도우에 표시될 내용입니다
        
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

      // message창 3초 후에 종료 
      setTimeout(function(){ 
        infowindow.close()
        marker.setMap(null);}, 3000)
      
      // 지도 중심좌표를 접속위치로 변경합니다
      map.setCenter(locPosition);   
      }  
    },
    // 핀 정보 불러오기
    getPlace() {},
  },
}
</script>

<style>

</style>
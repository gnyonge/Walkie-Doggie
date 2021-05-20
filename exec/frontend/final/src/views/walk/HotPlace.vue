<template>
  <div>
    <div id="app">
      <!-- 지도 -->
      <div id="map"></div>
    </div>
    <div class="m-4">
      <DropDown v-if="this.getSelectedItem === null" />
    </div>
    <ImageList v-if="this.getSelectedItem === null"/>
    <ImageItem v-else/>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import ImageList from '@/components/walk/ImageList'
import ImageItem from '@/components/walk/ImageItem'
import DropDown from '@/components/walk/DropDown'

export default {
  name: 'HotPlace',
  components: {
    ImageList,
    ImageItem,
    DropDown,
  },
  computed: {
    ...mapGetters(['getSelectedItem', 'getHotPlace', 'getUser', 'getDogInfo'])
  },
  data(){
    return{
      map: {},
      positions: [],
      myPin: true,
    }
  },
  created() {
    if (this.getUser == undefined) {
        alert('로그인 해주세요!')
        this.$router.push('/')
      } 
  },
  mounted() {
    // 멍플레이스 게시글들 받기 
    this.getHotPlaceListInApi({
        uid: this.getUser.uid,
        sort: "string",
        p_location: this.getUser.u_location
      }).then(() =>{
      var hotplace = this.getHotPlace
      for (var i of hotplace) {
          // 조건에 맞는 핀 이미지 연결 
          let img = ''
          if(i.l_desc === '사진이 잘 나와요!'){
            img = 'https://i.ibb.co/XWGzFdp/nicephoto.png'
          }else if(i.l_desc === '날씨가 좋아요!'){
            img = 'https://i.ibb.co/LhFSjwN/niceweather.png'
          }else if(i.l_desc === '마킹을 했어요!') {
            img = ' https://i.ibb.co/W2s6GbW/marking.png'
          }else{ // 휴식 공간이 있어요!
            img = 'https://i.ibb.co/pybCvBz/resting.png'
          } 
          this.positions.push({
          title: i.lid,
          lat: i.p_latitude,
          lon: i.p_longtitude,
          imageSrc: img
        })
          // 카카오 map 
          if (window.kakao && window.kakao.maps) {
            this.initMap();
            // 핀 꽂기 
            this.pin()
          } else {
          const script = document.createElement('script');
          // global kakao
          script.onload = () => kakao.maps.load(this.initMap);
          script.src = 'https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=6985aa694c9e9631fa03de6f22217f30';
          document.head.appendChild(script);
          }
      }
    })
  },
  methods: {
    ...mapActions(['getHotPlaceListInApi', 'setClickPostDetailInAPI', ]),
    // 지도 첫 화면 로드 
    initMap() {
      this.mapContainer = document.getElementById('map');
      this.mapOption = {
        //한강 주소 
        center: new kakao.maps.LatLng(37.507781796841705, 126.99274641516297),
        draggable: true,
        level: 5
      };
      
      var t = this
      t.map = new kakao.maps.Map(this.mapContainer, this.mapOption); // 지도를 생성합니다
      
      // HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
      if (navigator.geolocation) {
        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(function(position) { 
          var lat = position.coords.latitude, // 위도
              lon = position.coords.longitude; // 경도

          // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
          var locPosition = new kakao.maps.LatLng(lat, lon)
              
          // 마커와 인포윈도우를 표시합니다
          displayMarker(locPosition)
        });
      } else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
          var locPosition = new kakao.maps.LatLng(33.450701, 126.570667)
          displayMarker(locPosition,);
        }
   
      var map = this.map
      
      // 지도에 마커와 인포윈도우를 표시하는 함수입니다
      function displayMarker(locPosition) {
        var marker = new kakao.maps.Marker({
          position: locPosition
        });

        // 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(map);

        // 지도 중심좌표를 접속위치로 변경합니다
        map.setCenter(locPosition);   
        
      }  
      
    },
    // 핀 꽂기
    pin() {
      var t = this 
      var map = this.map
      var positions = this.positions
      for (var i = 0 ; i < positions.length; i++){
        // 마커 이미지의 이미지 크기 입니다
        var imageSize = new kakao.maps.Size(31, 35); 
        var imageOption = {offset: new kakao.maps.Point(16, 45)};
        // 마커 이미지 생성 
        var markerImage = new kakao.maps.MarkerImage(positions[i].imageSrc, imageSize, imageOption);
        //위치 정보 갱신 
        var latlng = new kakao.maps.LatLng(positions[i].lat, positions[i].lon)
        
        // 마커생성 
        var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: latlng, // 마커를 표시할 위치
        title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image : markerImage // 마커 이미지 
        });
        
        // 마커에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(marker, 'click', getMakerInfo(map, marker))  
      }
      //  마커 클릭시 해당 정보 가져오는 함수 
      function getMakerInfo(map, marker){
        return function(){
        // 마커 선택후 해당 정보 자식 컴포넌트로 전송 
        // 해당 게시글 wid
        console.log(t.getDogInfo.pet.uid)
        t.setClickPostDetailInAPI({
          uid:t.getDogInfo.pet.uid, 
          lid: marker.getTitle(),
        })
        }
      }
      marker.setMap(map)
      map.setDraggable(true)
    },
    
  },
}
</script>

<style>

</style>
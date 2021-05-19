import { rscApi } from '@/services/api';

const state = {
  dog: {
    // 멍플레이스 
    selectedItem: null ,
  },
  like: {
    // 좋아요 포스팅 
    nowLat: Number,
    nowLon: Number,
    tempPhotoUrl: '',
    likeCnt: 0, 
    // 지역설정 관련
    startArea: '', 
    areaName: '', 
    // 포스팅 후 
    wid: Number, 
  },
  // 이동 경로 
  path: [],
  // 핫플레이스 전체리스트, top5 담는 곳
  hotPlace: [],
  postingWid: [],
  myPostingContent: [],
  // 시작 시간 관련 
  startTime: '',
  beforeH: '', 
  beforeM: '', 
  filter: "",
};
const getters = {
  //처음 시작했을때 wid
  getWid(state){
    return state.like.wid
  },
  // 멍플레이스 
  getSelectedItem(state) {
    return state.dog.selectedItem
  },
  // 좋아요 포스팅 
  getNowLat(state){
    return state.like.nowLat
  },
  getNowLon(state){
    return state.like.nowLon
  },
  getTempPhotoURL(state){
    return state.like.tempPhotoUrl
  },
  getFirstAreaName(state){
    return state.like.startArea
  },
  getAreaName(state){
    return state.like.areaName
  },
  getLikeCnt(state){
    return state.like.likeCnt
  },
  // 경로 
  getMyPath(state){
    return state.path
  },
  // 내가 쓴 게시글 관련 
  getPostingWid(state){
    return state.postingWid
  },
  getMyPostingContent(state){
    return state.myPostingContent
  },
  getHotPlace(state) {
    return state.hotPlace
  },
  // 시작시간 가져오기
  getStartTime(state){
    return state.startTime
  },
  // 산책 시간 계산 
  getbeforeH(state){
    return state.beforeH
  },
  getbeforeM(state){
    return state.beforeM
  },
  getFilter(state){
    return state.filter
  },
  
};
const mutations = {
  // 글 작성 후 wid 
  setWid(state, data){
    state.like.wid = data 
  },
  // 멍플레이스 
  setSelectedItem(state, data){
    state.dog.selectedItem = data
  },
  // 좋아요 포스팅 
  setNowLat(state, data){
    state.like.nowLat = data
  },
  setNowLon(state, data){
    state.like.nowLon = data 
  },
  // 임시 사진 url 
  setTempPhotoURL(state, data){
    state.like.tempPhotoUrl = data 
  },
  // 산책시작 지역정보 
  setFirstAreaName(state, data){
    state.like.startArea = data
  },
  // 지역정보
  setAreaName(state, data){
    state.like.areaName = data
  },
  // 내가 간 경로 
  setMyPath(state, data){
    state.path.push(data)
  },
  // 경로 삭제
  deleteMyPath(state){
    state.path = []
  },
  // posting한 게시글 id 
  setPostingWid(state, data){
    state.postingWid.push(data)
  },
  //멍플레이스 전체글 
  setHotPlace(state, data) {
    state.hotPlace = data
  },
  // 내가 쓴 게시글들 wid 지우기
  deletePostingWid(state){
    state.postingWid = []
  },
  //내가 쓴 게시글 요청 return값 
  setMyPostingContent(state, data){
    state.myPostingContent = data
  },
  // 내가 쓴 게시글 내용 지우기 
  deletePostingContent(state){
    state.myPostingContent = []
  },
  setStartTime(state, data){
    state.startTime = data 
  },
  setbeforeH(state, data){
    state.beforeH = data 
  },
  setbeforeM(state, data){
    state.beforeM = data 
  },
  // 좋아요 게시글 올린 갯수 갱신
  setLikeCnt(state, data){
    state.like.likeCnt = data
  },
  setFilter(state, data){
    state.filter = data
  },
};
const actions = {
  // 산책 시작
  sendStartWalkInApi({commit}, paramas){
    return rscApi.post('walk/start', paramas)
      .then((res)=>{
        commit('setWid', res.data.wid)
      }).catch((error)=>{
        console.log(error)
      })
  },
  // 업로드 사진 미리보기 
  makeTempPhotoUrlInApi(context, params){
    return rscApi.post('place/imageUpload', params)
      .then((res)=> {
        // tempPhotoURL 갱신 
        context.commit('setTempPhotoURL', res.data.url)
      })
  },
  // 좋아요 사진, 위도, 경도, 의견 백엔드 전송
  sendNowPostInApi({commit, state}, params){
    return rscApi.post('place/likePlace', params)
      .then((res) =>{
        console.log(res)
        commit('setPostingWid', res.data.lid)
        console.log(state.postingWid,'게시글 작성후 postingwid들어갔냐' )
        return res
      }).catch((error) =>{
        // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답
        if(error.response){ 
          return error.response
        }else if (error.request){  // 요청이 이루어 졌으나 응답을 받지 못했습니다.
          return error.request
        }else { // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생
          return error.message
        }
      })
  },
  // 산책 종료 
  doneWalkInApi(context, params){
    console.log(params, '산책종료함수에 넘겨주는 params')
    return rscApi.put('walk/end', params)
      .then((res) => {
        console.log(res)
      }).catch((error) =>{
        console.log(error)
      })
  },
  // 내가 쓴 게시글 리스트 받아오기 
  getMyPlaceListInApi(context, params){
    return rscApi.post('walk/likeList',params)
      .then((res) => {
        context.commit('setMyPostingContent', res.data.likeList)
        return res
      }).catch((error) =>{
        console.log(error, '내가 쓴 게시글 가져오기 실패')
      })
  },
  // 핫플레이스 전체 리스트 받아오기
  getHotPlaceListInApi(context, params) {
    return rscApi.post('place/list', params)
    .then((res) => {
      console.log(res)
      context.commit('setHotPlace', res.data.postList)
    })
  },
  // 핫플레이스 TOP 5 받아오기
  getTop5ListInApi(context, params) {
    return rscApi.post('place/top5', params)
    .then((res) => {
      console.log(res)
      context.commit('setHotPlace', res.data.postList)
    })
  },
  // 핫플 포스트 디테일 
  setClickPostDetailInAPI(context,params){
    return rscApi.get(`place/detail/${params.lid}/${params.uid}`)
      .then((res)=> {
        context.commit('setSelectedItem', res.data.postDetail)
      }).catch((error)=>{
        console.log(error)
      })
  },
  // 내 게시글 삭제
  deleteMyPostingInApi(context, params){
    console.log(params)
    return rscApi.delete(`place/delete/${params.peid}?lid=${params.lid}`)
      .then((res)=>{
        console.log('내 게시글 삭제 성공')
        return res
      }).catch((error)=>{
        console.log(error)
      })
  }, 
  // 핫플레이스 포스트 좋아요
  likePlaceInApi(context, params) {
    return rscApi.post(`place/likePost?lid=${params.lid}&uid=${params.uid}`)
    .then((res) => {
      console.log(res, '좋아요 했음')
      return res.data.message
    })
    .catch((err) => {
      console.log(err)
    })
  },

};

export default {
  state: {
    ...state,
  },
  getters,
  mutations,
  actions,
};

import Vue from 'vue'
import VueRouter from 'vue-router'

import Calendar from '../views/calendar/Calendar.vue'
import CalendarWrite from '../views/calendar/CalendarWrite.vue'
import CalendarUpdate from '../views/calendar/CalendarUpdate.vue'
import CalendarDetail from '../views/calendar/CalendarDetail.vue'
import TodayDiary from '../views/calendar/TodayDiary.vue'
import TodayWalk from '../views/calendar/TodayWalk.vue'

import Walk from '../views/walk/Walk.vue'
import StartWalk from '../views/walk/StartWalk.vue'
import HotPlace from '../views/walk/HotPlace.vue'
import LikePosting from '../views/walk/LikePosting.vue'

import Food from '../views/food/Food.vue'
import FoodResult from '../views/food/FoodResult.vue'

import MyPage from '../views/mypage/MyPage.vue'
import Login from '../views/Login.vue'
import LoginMain from '../views/LoginMain.vue'
import Signup from '../views/Signup.vue'
import Register from '../views/register/Register.vue'
import DogRegister from '../views/register/DogRegister.vue'
import DogRegister2 from '../views/register/DogRegister2.vue'
import DogChange from '../views/mypage/DogChange.vue'
import Health from '../views/mypage/Health.vue'
import InfoChangeLocation from '../views/mypage/InfoChangeLocation.vue'
import InfoChangePassword from '../views/mypage/InfoChangePassword.vue'
import Statistics from '../views/mypage/Statistics.vue'
import Dontleaveus from '../views/mypage/Dontleaveus.vue'
import NotFound from '../views/NotFound.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: LoginMain,
  },
  {
    path: '/calendar',
    name: 'Calendar',
    component: Calendar,
  },
  {
    path: '/calendar/write',
    name: 'CalendarWrite',
    component: CalendarWrite
  },
  {
    path: '/calendar/update',
    name: 'CalendarUpdate',
    component: CalendarUpdate
  },
  {
    path: '/calendar/detail',
    name: 'CalendarDetail',
    component: CalendarDetail,
    children: [
      {
        path: 'todaydiary/:date',
        name: 'TodayDiary',
        component: TodayDiary
      },
      {
        path: 'todaywalk/:date',
        name: 'TodayWalk',
        component: TodayWalk
      },
    ]
  },
  {
    path: '/walk',
    name: 'Walk',
    component: Walk
  },
  {
    path: '/startwalk',
    name: 'StartWalk',
    component: StartWalk
  },
  {
    path: '/hotplace',
    name: 'HotPlace',
    component: HotPlace
  },
  {
    path: '/likeposting',
    name: 'LikePosting',
    component: LikePosting
  },
  {
    path: '/food',
    name: 'Food',
    component: Food
  },
  {
    path: '/food/result',
    name: 'FoodResult',
    component: FoodResult
  },
  {
    path: '/mypage',
    name: 'MyPage',
    component: MyPage
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/loginmain',
    name: 'LoginMain',
    component: LoginMain
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/dogregister',
    name: 'DogRegister',
    component: DogRegister
  },
  {
    path: '/dogregisternext',
    name: 'DogRegister2',
    component: DogRegister2
  },
  {
    path: '/dogchange',
    name: 'DogChange',
    component: DogChange
  },
  {
    path: '/health',
    name: 'Health',
    component: Health
  },
  {
    path: '/infochangelocation',
    name: 'InfoChangeLocation',
    component: InfoChangeLocation
  },
  {
    path: '/infochangepw',
    name: 'InfoChangePassword',
    component: InfoChangePassword
  },
  {
    path: '/statistics',
    name: 'Statistics',
    component: Statistics
  },
  {
    path: '/dontleaveus',
    name: 'Dontleaveus',
    component: Dontleaveus
  },
  { 
    path: '*', 
    redirect:'/404'},
  {
    path: "/404",
    component: NotFound
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
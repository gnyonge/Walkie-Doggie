import Vue from 'vue'
import VueRouter from 'vue-router'

import Calendar from '../views/calendar/Calendar.vue'
import TodayWalk from '../views/calendar/TodayWalk.vue'

import Walk from '../views/Walk/Walk.vue'
import StartWalk from '../views/Walk/StartWalk.vue'
import HotPlace from '../views/Walk/HotPlace.vue'

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
import InfoChange from '../views/mypage/InfoChange.vue'
import Statistics from '../views/mypage/Statistics.vue'



Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Calendar
  },
  {
    path: '/calendar',
    name: 'Calendar',
    component: Calendar,
    children: [
      {
        path: 'write',
        name: 'CalendarWrite',
        component: Calendar
      },
      {
        path: 'todaywalk',
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
    path: '/infochange',
    name: 'InfoChange',
    component: InfoChange
  },
  {
    path: '/statistics',
    name: 'Statistics',
    component: Statistics
  },
  // {
  //   path: '/about',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

import Vue from 'vue'
import VueRouter from 'vue-router'
import Calendar from '../views/calendar/Calendar.vue'
import Walk from '../views/Walk.vue'
import Food from '../views/Food.vue'
import Community from '../views/Community.vue'
import MyPage from '../views/MyPage.vue'
import TodayDiary from '../views/calendar/TodayDiary.vue'
import TodayWalk from '../views/calendar/TodayWalk.vue'

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
        path: 'todaydiary',
        name: 'TodayDiary',
        component: TodayDiary
      },
      {
        path: 'todaywalk',
        name: 'TodayWalk',
        component: TodayWalk
      },
    ]
  },
  // {
  //   path: '/calendar/todaydiary',
  //   name: 'TodayDiary',
  //   component: TodayDiary
  // },
  // {
  //   path: '/calendar/todaywalk',
  //   name: 'TodayWalk',
  //   component: TodayWalk
  // },
  {
    path: '/walk',
    name: 'Walk',
    component: Walk
  },
  {
    path: '/food',
    name: 'Food',
    component: Food
  },
  {
    path: '/community',
    name: 'Community',
    component: Community
  },
  {
    path: '/mypage',
    name: 'MyPage',
    component: MyPage
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

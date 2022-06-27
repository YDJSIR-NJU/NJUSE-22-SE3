import router from "@/router";

export const API_VERSION = '/api'

export const USER_MODULE = `${API_VERSION}/user`
export const USER_ACTIVENESS_MODULE = `${API_VERSION}/activeness`
export const USER_CAPABILITIES_MODULE = `${API_VERSION}/usercapability`

export const TASK_MODULE = `${API_VERSION}/task`
export const TASK_RATING_MODULE = `${API_VERSION}/taskRating`

export const REPORT_MODULE = `${API_VERSION}/defectReport`
export const RECOMMEDN_MODULE = `${API_VERSION}/recommend`
export const SIM_REPORT_MODULE = `${API_VERSION}/Sim`
export const ADDITIONAL_REPORT_MODULE = `${API_VERSION}/additionalReport`
export const REPORT_REMARK_MODULE = `${API_VERSION}/reportRemark`

export const MESSAGE_MODULE = `${API_VERSION}/message`


export const updateToken = (response) => {
  if (response.data.code === 3 || response.data.code === 4) {
    // alert("请重新登陆！")
    // 移除登陆状态
    window.localStorage.removeItem('id')
    window.localStorage.removeItem('uname')
    window.localStorage.removeItem('userId')
    router.push("/login")
  }
  if (response.headers['authorization'] != null) {
    console.log("Token Updated!")
    window.localStorage.setItem('token', response.headers['authorization'])
  }
}


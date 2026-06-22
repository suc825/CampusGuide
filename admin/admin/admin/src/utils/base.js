const base = {
    get() {
        return {
            url : "http://localhost:8080/SchoolMap/",
            name: "SchoolMap",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/SchoolMap/front/h5/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "中南林校园导览小程序"
        } 
    }
}
export default base

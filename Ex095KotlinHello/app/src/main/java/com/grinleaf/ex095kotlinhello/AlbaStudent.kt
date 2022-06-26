package com.grinleaf.ex095kotlinhello

class AlbaStudent(name:String, age:Int, major:String, var task:String):Student(name, age, major) {
    //주 생성자 키워드 생략된 상태
    init {
        println("create AlbaStudent instance")
    }

    override fun show() {
        //super.show()
        println("name : $name  age : $age  major : $major  task : $task")
    }
}
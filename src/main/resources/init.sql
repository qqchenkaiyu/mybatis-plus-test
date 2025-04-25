
-- 创建学生表
CREATE TABLE IF NOT EXISTS students (
    -- 学生ID，作为主键，自增
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    -- 学生姓名，不能为空
    student_name VARCHAR(100) NOT NULL,
    -- 学生年龄
    age INT,
    -- 学生性别，'M' 代表男性，'F' 代表女性
    gender CHAR(1),

    -- 学生入学日期
    enrollment_date DATE
);

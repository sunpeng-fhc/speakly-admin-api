-- =========================================
-- 删除旧表
-- =========================================

DROP TABLE IF EXISTS lesson_vocabularies;
DROP TABLE IF EXISTS lesson_segments;
DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS categories;


-- =========================================
-- 1. 分类表 categories
-- =========================================

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    icon VARCHAR(255),
    cover_image VARCHAR(255),
    theme_color VARCHAR(20),
    sort_order INT DEFAULT 0,
    is_featured BOOLEAN DEFAULT FALSE,
    status BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE categories IS '课程分类表';

COMMENT ON COLUMN categories.id IS '分类ID';
COMMENT ON COLUMN categories.name IS '分类名称';
COMMENT ON COLUMN categories.slug IS '分类唯一标识';
COMMENT ON COLUMN categories.description IS '分类描述';
COMMENT ON COLUMN categories.icon IS '分类图标';
COMMENT ON COLUMN categories.cover_image IS '分类封面图';
COMMENT ON COLUMN categories.theme_color IS '分类主题色';
COMMENT ON COLUMN categories.sort_order IS '排序';
COMMENT ON COLUMN categories.is_featured IS '是否推荐';
COMMENT ON COLUMN categories.status IS '状态';
COMMENT ON COLUMN categories.created_at IS '创建时间';
COMMENT ON COLUMN categories.updated_at IS '更新时间';


-- =========================================
-- 2. 课程表 lessons
-- =========================================

CREATE TABLE lessons (
     id BIGSERIAL PRIMARY KEY,

     category_id BIGINT NOT NULL,

     title VARCHAR(200) NOT NULL,

     slug VARCHAR(200) NOT NULL UNIQUE,

     summary TEXT,

     cover_image VARCHAR(255),

     audio_url VARCHAR(500),

     duration_seconds INT,

     level VARCHAR(20),

     transcript TEXT,

     status BOOLEAN DEFAULT TRUE,

     is_featured BOOLEAN DEFAULT FALSE,

     sort_order INT DEFAULT 0,

     published_at TIMESTAMP,

     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

     CONSTRAINT fk_lessons_category
         FOREIGN KEY (category_id)
             REFERENCES categories(id)
             ON DELETE RESTRICT
);

COMMENT ON TABLE lessons IS '课程表';

COMMENT ON COLUMN lessons.id IS '课程ID';
COMMENT ON COLUMN lessons.category_id IS '所属分类ID';
COMMENT ON COLUMN lessons.title IS '课程标题';
COMMENT ON COLUMN lessons.slug IS '课程唯一标识';
COMMENT ON COLUMN lessons.summary IS '课程简介';
COMMENT ON COLUMN lessons.cover_image IS '课程封面图';
COMMENT ON COLUMN lessons.audio_url IS '音频地址';
COMMENT ON COLUMN lessons.duration_seconds IS '音频时长（秒）';
COMMENT ON COLUMN lessons.level IS '课程等级，例如 A1/A2';
COMMENT ON COLUMN lessons.transcript IS '完整文章内容';
COMMENT ON COLUMN lessons.status IS '状态';
COMMENT ON COLUMN lessons.is_featured IS '是否推荐';
COMMENT ON COLUMN lessons.sort_order IS '排序';
COMMENT ON COLUMN lessons.published_at IS '发布时间';
COMMENT ON COLUMN lessons.created_at IS '创建时间';
COMMENT ON COLUMN lessons.updated_at IS '更新时间';


-- =========================================
-- 3. 音频句子分段表 lesson_segments
-- =========================================

CREATE TABLE lesson_segments (
     id BIGSERIAL PRIMARY KEY,

     lesson_id BIGINT NOT NULL,

     start_time NUMERIC(10,3) NOT NULL,

     end_time NUMERIC(10,3) NOT NULL,

     sentence TEXT NOT NULL,

     translation TEXT,

     sort_order INT DEFAULT 0,

     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

     CONSTRAINT fk_segments_lesson
         FOREIGN KEY (lesson_id)
             REFERENCES lessons(id)
             ON DELETE CASCADE
);

COMMENT ON TABLE lesson_segments IS '课程音频句子分段表';

COMMENT ON COLUMN lesson_segments.id IS '分段ID';
COMMENT ON COLUMN lesson_segments.lesson_id IS '所属课程ID';
COMMENT ON COLUMN lesson_segments.start_time IS '句子开始时间';
COMMENT ON COLUMN lesson_segments.end_time IS '句子结束时间';
COMMENT ON COLUMN lesson_segments.sentence IS '英文句子';
COMMENT ON COLUMN lesson_segments.translation IS '中文翻译';
COMMENT ON COLUMN lesson_segments.sort_order IS '排序';
COMMENT ON COLUMN lesson_segments.created_at IS '创建时间';
COMMENT ON COLUMN lesson_segments.updated_at IS '更新时间';


-- =========================================
-- 4. 重点词汇表 lesson_vocabularies
-- =========================================

CREATE TABLE lesson_vocabularies (
     id BIGSERIAL PRIMARY KEY,

     lesson_id BIGINT NOT NULL,

     word VARCHAR(100) NOT NULL,

     phonetic VARCHAR(100),

     part_of_speech VARCHAR(50),

     meaning TEXT,

     simple_definition TEXT,

     example_sentence TEXT,

     sort_order INT DEFAULT 0,

     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

     CONSTRAINT fk_vocabularies_lesson
         FOREIGN KEY (lesson_id)
             REFERENCES lessons(id)
             ON DELETE CASCADE
);

COMMENT ON TABLE lesson_vocabularies IS '课程重点词汇表';

COMMENT ON COLUMN lesson_vocabularies.id IS '词汇ID';
COMMENT ON COLUMN lesson_vocabularies.lesson_id IS '所属课程ID';
COMMENT ON COLUMN lesson_vocabularies.word IS '英文单词';
COMMENT ON COLUMN lesson_vocabularies.phonetic IS '音标';
COMMENT ON COLUMN lesson_vocabularies.part_of_speech IS '词性';
COMMENT ON COLUMN lesson_vocabularies.meaning IS '中文意思';
COMMENT ON COLUMN lesson_vocabularies.simple_definition IS '英文简单释义';
COMMENT ON COLUMN lesson_vocabularies.example_sentence IS '例句';
COMMENT ON COLUMN lesson_vocabularies.sort_order IS '排序';
COMMENT ON COLUMN lesson_vocabularies.created_at IS '创建时间';
COMMENT ON COLUMN lesson_vocabularies.updated_at IS '更新时间';
-- Table: public.admin_button

-- DROP TABLE IF EXISTS public.admin_button;

CREATE TABLE IF NOT EXISTS public.admin_button
(
    id bigint NOT NULL DEFAULT nextval('admin_button_id_seq'::regclass),
    button_code character varying(100) COLLATE pg_catalog."default" NOT NULL,
    button_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    enabled boolean DEFAULT true,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT admin_button_pkey PRIMARY KEY (id),
    CONSTRAINT admin_button_button_code_key UNIQUE (button_code)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.admin_button
    OWNER to postgres;

COMMENT ON TABLE public.admin_button
    IS '按钮权限表';

COMMENT ON COLUMN public.admin_button.id
    IS '按钮权限主键ID';

COMMENT ON COLUMN public.admin_button.button_code
    IS '按钮权限编码';

COMMENT ON COLUMN public.admin_button.button_name
    IS '按钮名称';

COMMENT ON COLUMN public.admin_button.description
    IS '按钮描述';

COMMENT ON COLUMN public.admin_button.enabled
    IS '是否启用';

COMMENT ON COLUMN public.admin_button.created_at
    IS '创建时间';




-- Table: public.admin_menu

-- DROP TABLE IF EXISTS public.admin_menu;

CREATE TABLE IF NOT EXISTS public.admin_menu
(
    id bigint NOT NULL DEFAULT nextval('admin_menu_id_seq'::regclass),
    parent_id bigint,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    path character varying(200) COLLATE pg_catalog."default" NOT NULL,
    component character varying(200) COLLATE pg_catalog."default",
    title character varying(200) COLLATE pg_catalog."default",
    icon character varying(100) COLLATE pg_catalog."default",
    keep_alive boolean DEFAULT false,
    is_hide boolean DEFAULT false,
    is_hide_tab boolean DEFAULT false,
    is_full_page boolean DEFAULT false,
    is_first_level boolean DEFAULT false,
    active_path character varying(200) COLLATE pg_catalog."default",
    link character varying(500) COLLATE pg_catalog."default",
    sort_order integer DEFAULT 0,
    enabled boolean DEFAULT true,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT admin_menu_pkey PRIMARY KEY (id),
    CONSTRAINT fk_admin_menu_parent FOREIGN KEY (parent_id)
    REFERENCES public.admin_menu (id) MATCH SIMPLE
                         ON UPDATE NO ACTION
                         ON DELETE CASCADE
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.admin_menu
    OWNER to postgres;

COMMENT ON TABLE public.admin_menu
    IS '后台菜单表';

COMMENT ON COLUMN public.admin_menu.id
    IS '菜单主键ID';

COMMENT ON COLUMN public.admin_menu.parent_id
    IS '父级菜单ID';

COMMENT ON COLUMN public.admin_menu.name
    IS '路由名称';

COMMENT ON COLUMN public.admin_menu.path
    IS '路由路径';

COMMENT ON COLUMN public.admin_menu.component
    IS '前端组件路径';

COMMENT ON COLUMN public.admin_menu.title
    IS '菜单标题';

COMMENT ON COLUMN public.admin_menu.icon
    IS '菜单图标';

COMMENT ON COLUMN public.admin_menu.keep_alive
    IS '是否缓存页面';

COMMENT ON COLUMN public.admin_menu.is_hide
    IS '是否隐藏菜单';

COMMENT ON COLUMN public.admin_menu.is_hide_tab
    IS '是否隐藏Tab';

COMMENT ON COLUMN public.admin_menu.is_full_page
    IS '是否全屏页面';

COMMENT ON COLUMN public.admin_menu.is_first_level
    IS '是否一级菜单';

COMMENT ON COLUMN public.admin_menu.active_path
    IS '激活菜单路径';

COMMENT ON COLUMN public.admin_menu.link
    IS '外部链接';

COMMENT ON COLUMN public.admin_menu.sort_order
    IS '菜单排序';

COMMENT ON COLUMN public.admin_menu.enabled
    IS '是否启用';

COMMENT ON COLUMN public.admin_menu.created_at
    IS '创建时间';

COMMENT ON COLUMN public.admin_menu.updated_at
    IS '更新时间';



-- Table: public.admin_role

-- DROP TABLE IF EXISTS public.admin_role;

CREATE TABLE IF NOT EXISTS public.admin_role
(
    id bigint NOT NULL DEFAULT nextval('admin_role_id_seq'::regclass),
    role_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    role_code character varying(50) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    enabled boolean DEFAULT true,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT admin_role_pkey PRIMARY KEY (id),
    CONSTRAINT admin_role_role_code_key UNIQUE (role_code)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.admin_role
    OWNER to postgres;

COMMENT ON TABLE public.admin_role
    IS '后台角色表';

COMMENT ON COLUMN public.admin_role.id
    IS '角色主键ID';

COMMENT ON COLUMN public.admin_role.role_name
    IS '角色名称';

COMMENT ON COLUMN public.admin_role.role_code
    IS '角色编码';

COMMENT ON COLUMN public.admin_role.description
    IS '角色描述';

COMMENT ON COLUMN public.admin_role.enabled
    IS '是否启用';

COMMENT ON COLUMN public.admin_role.created_at
    IS '创建时间';

COMMENT ON COLUMN public.admin_role.updated_at
    IS '更新时间';



-- Table: public.admin_role_button

-- DROP TABLE IF EXISTS public.admin_role_button;

CREATE TABLE IF NOT EXISTS public.admin_role_button
(
    id bigint NOT NULL DEFAULT nextval('admin_role_button_id_seq'::regclass),
    role_id bigint NOT NULL,
    button_id bigint NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT admin_role_button_pkey PRIMARY KEY (id),
    CONSTRAINT uk_admin_role_button UNIQUE (role_id, button_id),
    CONSTRAINT fk_admin_role_button_button FOREIGN KEY (button_id)
    REFERENCES public.admin_button (id) MATCH SIMPLE
                         ON UPDATE NO ACTION
                         ON DELETE CASCADE,
    CONSTRAINT fk_admin_role_button_role FOREIGN KEY (role_id)
    REFERENCES public.admin_role (id) MATCH SIMPLE
                         ON UPDATE NO ACTION
                         ON DELETE CASCADE
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.admin_role_button
    OWNER to postgres;

COMMENT ON TABLE public.admin_role_button
    IS '角色按钮权限关联表';

COMMENT ON COLUMN public.admin_role_button.id
    IS '关联主键ID';

COMMENT ON COLUMN public.admin_role_button.role_id
    IS '角色ID';

COMMENT ON COLUMN public.admin_role_button.button_id
    IS '按钮权限ID';

COMMENT ON COLUMN public.admin_role_button.created_at
    IS '创建时间';


-- Table: public.admin_role_menu

-- DROP TABLE IF EXISTS public.admin_role_menu;

CREATE TABLE IF NOT EXISTS public.admin_role_menu
(
    id bigint NOT NULL DEFAULT nextval('admin_role_menu_id_seq'::regclass),
    role_id bigint NOT NULL,
    menu_id bigint NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT admin_role_menu_pkey PRIMARY KEY (id),
    CONSTRAINT uk_admin_role_menu UNIQUE (role_id, menu_id),
    CONSTRAINT fk_admin_role_menu_menu FOREIGN KEY (menu_id)
    REFERENCES public.admin_menu (id) MATCH SIMPLE
                         ON UPDATE NO ACTION
                         ON DELETE CASCADE,
    CONSTRAINT fk_admin_role_menu_role FOREIGN KEY (role_id)
    REFERENCES public.admin_role (id) MATCH SIMPLE
                         ON UPDATE NO ACTION
                         ON DELETE CASCADE
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.admin_role_menu
    OWNER to postgres;

COMMENT ON TABLE public.admin_role_menu
    IS '角色菜单关联表';

COMMENT ON COLUMN public.admin_role_menu.id
    IS '关联主键ID';

COMMENT ON COLUMN public.admin_role_menu.role_id
    IS '角色ID';

COMMENT ON COLUMN public.admin_role_menu.menu_id
    IS '菜单ID';

COMMENT ON COLUMN public.admin_role_menu.created_at
    IS '创建时间';

-- Table: public.admin_user

-- DROP TABLE IF EXISTS public.admin_user;

CREATE TABLE IF NOT EXISTS public.admin_user
(
    id bigint NOT NULL DEFAULT nextval('admin_user_id_seq'::regclass),
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password_hash character varying(255) COLLATE pg_catalog."default" NOT NULL,
    gender smallint DEFAULT 1,
    mobile character varying(30) COLLATE pg_catalog."default",
    email character varying(100) COLLATE pg_catalog."default",
    department character varying(100) COLLATE pg_catalog."default",
    status character varying(10) COLLATE pg_catalog."default" DEFAULT '1'::character varying,
    avatar character varying(500) COLLATE pg_catalog."default",
    create_by character varying(50) COLLATE pg_catalog."default",
    update_by character varying(50) COLLATE pg_catalog."default",
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT admin_user_pkey PRIMARY KEY (id),
    CONSTRAINT admin_user_username_key UNIQUE (username)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.admin_user
    OWNER to postgres;

COMMENT ON TABLE public.admin_user
    IS '后台管理员用户表';

COMMENT ON COLUMN public.admin_user.id
    IS '用户主键ID';

COMMENT ON COLUMN public.admin_user.username
    IS '登录用户名';

COMMENT ON COLUMN public.admin_user.password_hash
    IS 'BCrypt加密后的密码';

COMMENT ON COLUMN public.admin_user.gender
    IS '性别：1=男，0=女';

COMMENT ON COLUMN public.admin_user.mobile
    IS '手机号';

COMMENT ON COLUMN public.admin_user.email
    IS '邮箱';

COMMENT ON COLUMN public.admin_user.department
    IS '所属部门';

COMMENT ON COLUMN public.admin_user.status
    IS '用户状态';

COMMENT ON COLUMN public.admin_user.avatar
    IS '头像URL';

COMMENT ON COLUMN public.admin_user.create_by
    IS '创建人';

COMMENT ON COLUMN public.admin_user.update_by
    IS '更新人';

COMMENT ON COLUMN public.admin_user.created_at
    IS '创建时间';

COMMENT ON COLUMN public.admin_user.updated_at
    IS '更新时间';

-- Table: public.admin_user_role

-- DROP TABLE IF EXISTS public.admin_user_role;

CREATE TABLE IF NOT EXISTS public.admin_user_role
(
    id bigint NOT NULL DEFAULT nextval('admin_user_role_id_seq'::regclass),
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT admin_user_role_pkey PRIMARY KEY (id),
    CONSTRAINT uk_admin_user_role UNIQUE (user_id, role_id),
    CONSTRAINT fk_admin_user_role_role FOREIGN KEY (role_id)
    REFERENCES public.admin_role (id) MATCH SIMPLE
                         ON UPDATE NO ACTION
                         ON DELETE CASCADE,
    CONSTRAINT fk_admin_user_role_user FOREIGN KEY (user_id)
    REFERENCES public.admin_user (id) MATCH SIMPLE
                         ON UPDATE NO ACTION
                         ON DELETE CASCADE
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.admin_user_role
    OWNER to postgres;

COMMENT ON TABLE public.admin_user_role
    IS '用户角色关联表';

COMMENT ON COLUMN public.admin_user_role.id
    IS '关联主键ID';

COMMENT ON COLUMN public.admin_user_role.user_id
    IS '用户ID';

COMMENT ON COLUMN public.admin_user_role.role_id
    IS '角色ID';

COMMENT ON COLUMN public.admin_user_role.created_at
    IS '创建时间';

-- Table: public.categories

-- DROP TABLE IF EXISTS public.categories;

CREATE TABLE IF NOT EXISTS public.categories
(
    id bigint NOT NULL DEFAULT nextval('categories_id_seq'::regclass),
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    slug character varying(100) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default",
    icon character varying(255) COLLATE pg_catalog."default",
    cover_image character varying(255) COLLATE pg_catalog."default",
    theme_color character varying(20) COLLATE pg_catalog."default",
    sort_order integer DEFAULT 0,
    is_featured boolean DEFAULT false,
    status boolean DEFAULT true,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    short_name character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT categories_pkey PRIMARY KEY (id),
    CONSTRAINT categories_slug_key UNIQUE (slug)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.categories
    OWNER to postgres;

COMMENT ON TABLE public.categories
    IS '课程分类表';

COMMENT ON COLUMN public.categories.id
    IS '分类ID';

COMMENT ON COLUMN public.categories.name
    IS '分类名称';

COMMENT ON COLUMN public.categories.slug
    IS '分类唯一标识';

COMMENT ON COLUMN public.categories.description
    IS '分类描述';

COMMENT ON COLUMN public.categories.icon
    IS '分类图标';

COMMENT ON COLUMN public.categories.cover_image
    IS '分类封面图';

COMMENT ON COLUMN public.categories.theme_color
    IS '分类主题色';

COMMENT ON COLUMN public.categories.sort_order
    IS '排序';

COMMENT ON COLUMN public.categories.is_featured
    IS '是否推荐';

COMMENT ON COLUMN public.categories.status
    IS '状态';

COMMENT ON COLUMN public.categories.created_at
    IS '创建时间';

COMMENT ON COLUMN public.categories.updated_at
    IS '更新时间';

-- Table: public.lesson_segments

-- DROP TABLE IF EXISTS public.lesson_segments;

CREATE TABLE IF NOT EXISTS public.lesson_segments
(
    id bigint NOT NULL DEFAULT nextval('lesson_segments_id_seq'::regclass),
    lesson_id bigint NOT NULL,
    start_time numeric(10,3) NOT NULL,
    end_time numeric(10,3) NOT NULL,
    sentence text COLLATE pg_catalog."default" NOT NULL,
    translation text COLLATE pg_catalog."default",
    sort_order integer DEFAULT 0,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT lesson_segments_pkey PRIMARY KEY (id),
    CONSTRAINT fk_segments_lesson FOREIGN KEY (lesson_id)
    REFERENCES public.lessons (id) MATCH SIMPLE
                         ON UPDATE NO ACTION
                         ON DELETE CASCADE
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.lesson_segments
    OWNER to postgres;

COMMENT ON TABLE public.lesson_segments
    IS '课程音频句子分段表';

COMMENT ON COLUMN public.lesson_segments.id
    IS '分段ID';

COMMENT ON COLUMN public.lesson_segments.lesson_id
    IS '所属课程ID';

COMMENT ON COLUMN public.lesson_segments.start_time
    IS '句子开始时间';

COMMENT ON COLUMN public.lesson_segments.end_time
    IS '句子结束时间';

COMMENT ON COLUMN public.lesson_segments.sentence
    IS '英文句子';

COMMENT ON COLUMN public.lesson_segments.translation
    IS '中文翻译';

COMMENT ON COLUMN public.lesson_segments.sort_order
    IS '排序';

COMMENT ON COLUMN public.lesson_segments.created_at
    IS '创建时间';

COMMENT ON COLUMN public.lesson_segments.updated_at
    IS '更新时间';


-- Table: public.lesson_vocabularies

-- DROP TABLE IF EXISTS public.lesson_vocabularies;

CREATE TABLE IF NOT EXISTS public.lesson_vocabularies
(
    id bigint NOT NULL DEFAULT nextval('lesson_vocabularies_id_seq'::regclass),
    lesson_id bigint NOT NULL,
    word character varying(100) COLLATE pg_catalog."default" NOT NULL,
    phonetic character varying(100) COLLATE pg_catalog."default",
    part_of_speech character varying(50) COLLATE pg_catalog."default",
    meaning text COLLATE pg_catalog."default",
    simple_definition text COLLATE pg_catalog."default",
    example_sentence text COLLATE pg_catalog."default",
    sort_order integer DEFAULT 0,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT lesson_vocabularies_pkey PRIMARY KEY (id),
    CONSTRAINT fk_vocabularies_lesson FOREIGN KEY (lesson_id)
    REFERENCES public.lessons (id) MATCH SIMPLE
                         ON UPDATE NO ACTION
                         ON DELETE CASCADE
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.lesson_vocabularies
    OWNER to postgres;

COMMENT ON TABLE public.lesson_vocabularies
    IS '课程重点词汇表';

COMMENT ON COLUMN public.lesson_vocabularies.id
    IS '词汇ID';

COMMENT ON COLUMN public.lesson_vocabularies.lesson_id
    IS '所属课程ID';

COMMENT ON COLUMN public.lesson_vocabularies.word
    IS '英文单词';

COMMENT ON COLUMN public.lesson_vocabularies.phonetic
    IS '音标';

COMMENT ON COLUMN public.lesson_vocabularies.part_of_speech
    IS '词性';

COMMENT ON COLUMN public.lesson_vocabularies.meaning
    IS '中文意思';

COMMENT ON COLUMN public.lesson_vocabularies.simple_definition
    IS '英文简单释义';

COMMENT ON COLUMN public.lesson_vocabularies.example_sentence
    IS '例句';

COMMENT ON COLUMN public.lesson_vocabularies.sort_order
    IS '排序';

COMMENT ON COLUMN public.lesson_vocabularies.created_at
    IS '创建时间';

COMMENT ON COLUMN public.lesson_vocabularies.updated_at
    IS '更新时间';



-- Table: public.lessons

-- DROP TABLE IF EXISTS public.lessons;

CREATE TABLE IF NOT EXISTS public.lessons
(
    id bigint NOT NULL DEFAULT nextval('lessons_id_seq'::regclass),
    category_id bigint NOT NULL,
    title character varying(200) COLLATE pg_catalog."default" NOT NULL,
    slug character varying(200) COLLATE pg_catalog."default" NOT NULL,
    summary text COLLATE pg_catalog."default",
    cover_image character varying(255) COLLATE pg_catalog."default",
    audio_url character varying(500) COLLATE pg_catalog."default",
    duration_seconds integer,
    level character varying(20) COLLATE pg_catalog."default",
    transcript text COLLATE pg_catalog."default",
    status boolean DEFAULT true,
    is_featured boolean DEFAULT false,
    sort_order integer DEFAULT 0,
    published_at timestamp without time zone,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    is_daily boolean DEFAULT false,
    daily_date date,
    CONSTRAINT lessons_pkey PRIMARY KEY (id),
    CONSTRAINT lessons_slug_key UNIQUE (slug),
    CONSTRAINT fk_lessons_category FOREIGN KEY (category_id)
    REFERENCES public.categories (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE RESTRICT
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.lessons
    OWNER to postgres;

COMMENT ON TABLE public.lessons
    IS '课程表';

COMMENT ON COLUMN public.lessons.id
    IS '课程ID';

COMMENT ON COLUMN public.lessons.category_id
    IS '所属分类ID';

COMMENT ON COLUMN public.lessons.title
    IS '课程标题';

COMMENT ON COLUMN public.lessons.slug
    IS '课程唯一标识';

COMMENT ON COLUMN public.lessons.summary
    IS '课程简介';

COMMENT ON COLUMN public.lessons.cover_image
    IS '课程封面图';

COMMENT ON COLUMN public.lessons.audio_url
    IS '音频地址';

COMMENT ON COLUMN public.lessons.duration_seconds
    IS '音频时长（秒）';

COMMENT ON COLUMN public.lessons.level
    IS '课程等级，例如 A1/A2';

COMMENT ON COLUMN public.lessons.transcript
    IS '完整文章内容';

COMMENT ON COLUMN public.lessons.status
    IS '状态';

COMMENT ON COLUMN public.lessons.is_featured
    IS '是否推荐';

COMMENT ON COLUMN public.lessons.sort_order
    IS '排序';

COMMENT ON COLUMN public.lessons.published_at
    IS '发布时间';

COMMENT ON COLUMN public.lessons.created_at
    IS '创建时间';

COMMENT ON COLUMN public.lessons.updated_at
    IS '更新时间';
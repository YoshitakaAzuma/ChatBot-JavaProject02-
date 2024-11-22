


create table users (
	id serial primary key,
	username varchar(50) unique not null,
	password_hash varchar(255) not null,
	created_at timestamp(6) without time zone
);

-- 権限用のENUM型
create type data as ENUM ('身長', '体重','性別','年齢','体脂肪率','血圧','歩数','食事','睡眠時間');

create table helth_data (
	id serial primary key,
	user_id integer references users(id),
	data_type data not null,
	value DECIMAL(10, 2) NOT NULL,
	description TEXT,
	recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 身長履歴を格納するテーブル
CREATE TABLE height_records (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    height DECIMAL(5, 2) NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 体重履歴を格納するテーブル
CREATE TABLE weight_records (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    weight DECIMAL(5, 2) NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 性別履歴を格納するテーブル
CREATE TABLE gender_records (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    gender VARCHAR(10) NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 年齢履歴を格納するテーブル
CREATE TABLE age_records (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    age INTEGER NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 体脂肪履歴を格納するテーブル
CREATE TABLE fat_records (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    fat DECIMAL(5, 2) NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 血圧履歴を格納するテーブル
CREATE TABLE blood_pressure_records (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    systolic INTEGER NOT NULL,
    diastolic INTEGER NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 歩数履歴を格納するテーブル
CREATE TABLE steps_records (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    steps INTEGER NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 食事履歴を格納するテーブル
CREATE TABLE meal_records (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    meal_description TEXT NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 睡眠履歴を格納するテーブル
CREATE TABLE sleep_records (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    sleep_duration DECIMAL(4, 2) NOT NULL, -- 睡眠時間を時間単位で記録
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

















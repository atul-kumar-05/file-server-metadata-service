create keyspace if not exists file_metadata
    with replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

use file_metadata;

create table if not exists file_metadata.file_by_id (
    file_id uuid primary key,
    user_id uuid,
    file_name text,
    file_size bigint,
    mime_type text,
    storage_path text,
    status text,
    created_at timestamp
);

create table if not exists file_metadata.files_by_user (
    user_id uuid,
    file_id uuid,
    file_name text,
    status text,
    created_at timestamp,
    primary key (user_id, created_at)
) with clustering order by (file_id desc);
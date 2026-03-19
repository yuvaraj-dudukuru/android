import os
import shutil

ROOT_DIR = r"c:\Users\ADMIN\StudioProjects\fraylon-android"
IGNORE_DIRS = {".git", ".gradle", ".idea", "build", "captures"}
EXTENSIONS = {".java", ".kt", ".xml", ".gradle", ".kts", ".pro", ".properties", ".json"}

# 1. Text Replacements
REPLACEMENTS = [
    ("com.owncloud.android", "com.fraylon.workspace"),
    ("com.nextcloud.client", "com.fraylon.workspace"),
    ("org.nextcloud", "org.fraylon"),
    # General renaming for authorities and strings
    ("com.owncloud", "com.fraylon"),
    ("com.nextcloud", "com.fraylon"),
    ("ownCloud", "Fraylon"),
    ("Nextcloud", "Fraylon")
]

def replace_in_files():
    count_files_modified = 0
    for root, dirs, files in os.walk(ROOT_DIR, topdown=True):
        dirs[:] = [d for d in dirs if d not in IGNORE_DIRS]
        
        for file in files:
            if not any(file.endswith(ext) for ext in EXTENSIONS):
                continue
            
            filepath = os.path.join(root, file)
            try:
                with open(filepath, "r", encoding="utf-8") as f:
                    content = f.read()
            except Exception:
                continue # skip unreadable files
                
            new_content = content
            for old_str, new_str in REPLACEMENTS:
                new_content = new_content.replace(old_str, new_str)
            
            if new_content != content:
                try:
                    with open(filepath, "w", encoding="utf-8", newline='') as f:
                        f.write(new_content)
                    count_files_modified += 1
                except Exception as e:
                    print(f"Failed to write {filepath}: {e}")
                    
    print(f"Modified {count_files_modified} files with string replacements.")

# 2. Directory Replacements
DIR_REPLACEMENTS = [
    (r"com\owncloud\android", r"com\fraylon\workspace"),
    (r"com\owncloud", r"com\fraylon"),
    (r"com\nextcloud\client", r"com\fraylon\workspace"),
    (r"com\nextcloud", r"com\fraylon"),
    (r"org\nextcloud", r"org\fraylon")
]

def move_directories():
    # We walk bottom-up so renaming a child doesn't break the parent loop
    for root, dirs, files in os.walk(ROOT_DIR, topdown=False):
        for dir_name in dirs:
            if dir_name in IGNORE_DIRS: continue
            
            path = os.path.join(root, dir_name)
            # Find any matching sub-path from tail
            for old_sub, new_sub in DIR_REPLACEMENTS:
                if path.endswith(old_sub):
                    # We need to compute the new path
                    parent = path[:-len(old_sub)]
                    new_path = os.path.join(parent, new_sub)
                    try:
                        os.makedirs(new_path, exist_ok=True)
                        for item in os.listdir(path):
                            s = os.path.join(path, item)
                            d = os.path.join(new_path, item)
                            shutil.move(s, d)
                        # Remove old dir if empty
                        os.rmdir(path)
                        # print(f"Moved {path} to {new_path}")
                        # If we moved com/owncloud/android, we don't want to process com/owncloud again in the same iteration on 'path'
                        break
                    except Exception as e:
                        pass
                        # print(f"Error moving {path}: {e}")

if __name__ == "__main__":
    replace_in_files()
    move_directories()
    # run generic move directories again just in case empty folders were left
    for root, dirs, files in os.walk(ROOT_DIR, topdown=False):
        for dir_name in dirs:
            path = os.path.join(root, dir_name)
            if not os.listdir(path) and ("owncloud" in path or "nextcloud" in path):
                try: os.rmdir(path)
                except: pass

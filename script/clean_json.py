import json
def main():
    # Path of the json file to clean
    filename = "../data/ijcnlp_dailydialog/train/train/clean_data.txt.json"
    # Name of the output json file
    output_filename = "../data/ijcnlp_dailydialog/train/train/shorter_clean_data.json"

    with open(filename, "r") as file:
        json_data = file.read()
        data = json.loads(json_data)

    return ""



if __name__ == "__main__":
    main()
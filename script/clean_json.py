import json
def main():
    # Path of the json file to clean
    filename = "../data/ijcnlp_dailydialog/train/train/clean_data.txt.json"
    # Name of the output json file
    output_filename = "../data/ijcnlp_dailydialog/train/train/shorter_clean_data.json"

    with open(filename, "r") as file:
        json_data = file.read()
        data = json.loads(json_data)
        sentence = data["sentences"]
        clean_json = {}
        for word in sentence:
            # print("word: " + str(word))
            sentence_index = word["index"]
            sentence_data = []
            sentence_token = word["tokens"]
            for token in sentence_token:
                pos = token["pos"]
                sentence_data.append(pos)
            clean_json[sentence_index] = sentence_data
    return clean_json



if __name__ == "__main__":
    data = main()
    print(data)

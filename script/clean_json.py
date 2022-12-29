import json
def main():
    # Path of the json file to clean
    filename = "../data/ijcnlp_dailydialog/train/train/clean_data.txt.json"
    # Name of the output json file
    output_filename = "../data/ijcnlp_dailydialog/train/train/shorter_clean_data.json"

    match_id = {"CC": 1, "CD": 2, "DT":3, "EX": 4, "FW": 5, "IN": 6, "JJ": 7, "JJR": 8, "JJS": 9, "LS": 10, "MD": 11, "NN": 12, "NNS": 13, "NNP": 14, "NNPS": 15, "PDT": 16, "POS": 17, "PRP": 18,
                "PRP$": 19, "RB": 20, "RBR": 21, "RBS": 22, "RP":23, "SYM": 24, "TO": 25, "UH": 26, "VB": 27, "VBD": 28, "VBG": 29, "VBN": 30, "VBP": 31, "VBZ": 32, "WDT": 33, "WP": 34, "WP$": 35, "WRB":36, "HYPH": 37, "NFP":38, "ADD":39 , "-LRB-":40, "-RRB-":41, "AFX":42, "GW": 43,".": 0, ",": -1, "!": -2, "?": -3, "''": -4, ":":-5, "``": -6, "$":-7}
    with open(filename, "r") as file:
        json_data = file.read()
        data = json.loads(json_data)
        sentence = data["sentences"]
        clean_json = {}
        for word in sentence:
            sentence_index = word["index"]
            sentence_data = []
            sentence_token = word["tokens"]
            for token in sentence_token:
                pos = token["pos"]
                pos_value = match_id[pos]
                sentence_data.append(pos_value)
            clean_json[sentence_index] = sentence_data
    with open(output_filename, "w") as file:
        data_string = json.dumps(clean_json)
        file.write(data_string)
    return 0



if __name__ == "__main__":
    data = main()
    print(data)

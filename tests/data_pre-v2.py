#!/bin/python3
import json
import sys

# Global vars
FILE_ALPHABET = "./EN-alphabet.txt"
FILE_WORDLIST = "./EN-wordlist.txt"
FILE_RESULTS  = "./EN-frequencies.json"

def usage():
    print(f"Usage (default files):")
    print(f"$ python3 {sys.argv[0]}")
    print(f"Usage (custom files):")
    print(f"$ python3 {sys.argv[0]} <alphabet_file> <wordlist_file> <results_file>")

def main():
    # Read + Store alphabet
    alphabet = []
    with open(FILE_ALPHABET, "r") as f_alphabet:
        aux = f_alphabet.readline()
        while aux:
            alphabet.append(aux.replace("\n",""))
            aux = f_alphabet.readline()
    # print(alphabet)

    # Read + Store wordlist
    wordlist = []
    with open(FILE_WORDLIST, "r") as f_wordlist:
        aux = f_wordlist.readline()
        while aux:
            aux = aux.replace("\n","")
            if (" " in aux):
                for word in aux.split(" "):
                    if word != "":
                        wordlist.append(word)
            else:
                wordlist.append(aux)
            
            aux = f_wordlist.readline()
    # print(wordlist)

    # Create Char + Frequency dict
    rf = "raw_frequencies"
    cf = "concatenated_frequencies"
    frequencies = {
            rf: {},
            cf: {}
    }
    
    for char in alphabet:
        frequencies[rf][char] = 0
        frequencies[cf][char] = {}

    for key in frequencies[cf]:
        for char in alphabet:
            frequencies[cf][key][char] = 0

    # Calculate frequencies
    total_chars = 0
    for word in wordlist:
        wlen = len(word)
        frequencies[rf][word[0].lower()] += 1
        idx = 1
        while idx < wlen:
            prev_char = word[idx-1].lower()
            curr_char = word[idx].lower()
            
            if (curr_char not in alphabet):
                print(f"[!] Missing char {curr_char}")
                sys.exit(-1)

            frequencies[rf][curr_char] += 1
            frequencies[cf][prev_char][curr_char] += 1
            total_chars += 1
            idx += 1

    
    for key in frequencies[cf]:
        for char in frequencies[cf][key]:
            frequencies[cf][key][char] = float(frequencies[cf][key][char]/frequencies[rf][key])

    for key in frequencies[rf]:
        frequencies[rf][key] = float(frequencies[rf][key]/total_chars)
    
    # Prints results json
    print(f"Char count: {total_chars}")
    print(f"Word count: {len(wordlist)}")

    # Save results
    with open(FILE_RESULTS, "w") as file:
        file.write(json.dumps(frequencies, indent=4))

    print(f"[+] Saved results in: {FILE_RESULTS}")


if __name__=="__main__":
    if (len(sys.argv) == 1):
        print("[!] Using default inputs / output:")
        print(f'''
    FILE_ALPHABET = {FILE_ALPHABET}
    FILE_WORDLIST = {FILE_WORDLIST}
    FILE_RESULTS  = {FILE_RESULTS}
                ''')

    elif (len(sys.argv) == 4) :
        print("[+] Using custom inputs / output")
        FILE_ALPHABET = sys.argv[1]
        FILE_WORDLIST = sys.argv[2]
        FILE_RESULTS  = sys.argv[3]

    else:
        usage()
        sys.exit(-1)
            
    main()

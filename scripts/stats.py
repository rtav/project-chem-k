#!/usr/bin/env python
arff = file("../resources/results.arff", 'r')
arff_lines = arff.readlines()
arff.close()

arff_lines = arff_lines[35:] # Remove first 35 lines.

data = {}
for line in arff_lines:
    idx = line.find("'", 2)
    compound = line[1:idx].strip()
    data[compound] = line[idx+2:].rstrip("\n")

compounds = {}
for compound in data:
    fields = data[compound].split(",")
    # Missing pH-values get set to 0.0
    if fields[1] == "?":
        fields[1] = 0.0
    items = [float(fields[0]), float(fields[1])]
    items.extend([int(field) for field in fields[2:]])
    compounds[compound] = {}
    compounds[compound]["kOH"] = items[0]
    compounds[compound]["pH"] = items[1]
    compounds[compound]["Alkyl_Carbon"] = items[2]
    compounds[compound]["Allenic_Carbon"] = items[3]
    compounds[compound]["Vinylic_Carbon"] = items[4]
    compounds[compound]["Acetylenic_Carbon"] = items[5]
    compounds[compound]["Arene"] = items[6]
    compounds[compound]["Carbonyl_with_Carbon"] = items[7]
    compounds[compound]["Carbonyl_with_Nitrogen"] = items[8]
    compounds[compound]["Carbonyl_with_Oxygen"] = items[9]
    compounds[compound]["Aldehyde"] = items[10]
    compounds[compound]["Acyl_Halide"] = items[11]
    compounds[compound]["Anhydride"] = items[12]
    compounds[compound]["Amide"] = items[13]
    compounds[compound]["Amidinium"] = items[14]
    compounds[compound]["Carboxylic_acid"] = items[15]
    compounds[compound]["Cyanamide"] = items[16]
    compounds[compound]["Ketone"] = items[17]
    compounds[compound]["Ether"] = items[18]
    compounds[compound]["Carbamate"] = items[19]
    compounds[compound]["Carbamic_ester"] = items[20]
    compounds[compound]["Hydroxyl"] = items[21]
    compounds[compound]["Hydroxyl_in_Alcohol"] = items[22]
    compounds[compound]["Enol"] = items[23]
    compounds[compound]["Phenol"] = items[24]
    compounds[compound]["Enol_or_Phenol"] = items[25]
    compounds[compound]["Peroxide_groups"] = items[26]
    compounds[compound]["Any_carbon_attached_to_any_halogen"] = items[27]
    compounds[compound]["Halogen"] = items[28]
    compounds[compound]["Three_halides_groups"] = items[29]

functional_groups = [
    "Alkyl_Carbon",
    "Allenic_Carbon",
    "Vinylic_Carbon",
    "Acetylenic_Carbon",
    "Arene",
    "Carbonyl_with_Carbon",
    "Carbonyl_with_Nitrogen",
    "Carbonyl_with_Oxygen",
    "Aldehyde",
    "Acyl_Halide",
    "Anhydride",
    "Amide",
    "Amidinium",
    "Carboxylic_acid",
    "Cyanamide",
    "Ketone",
    "Ether",
    "Carbamate",
    "Carbamic_ester",
    "Hydroxyl",
    "Hydroxyl_in_Alcohol",
    "Enol",
    "Phenol",
    "Enol_or_Phenol",
    "Peroxide_groups",
    "Any_carbon_attached_to_any_halogen",
    "Halogen",
    "Three_halides_groups"]

def mean(l):
    return sum(l) * 1.0 / len(l)

def median(l):
    return sorted(l)[len(l)/2]

print "#Functional group\tmin\tmax\tmean\tmedian"
for fg in functional_groups:
    l = [compounds[compound][fg] for compound in compounds]
    c_min = min(l)
    c_max = max(l)
    c_mean = mean(l)
    c_median = median(l)
    print "%s\t%d\t%d\t%f\t%f" % (fg, c_min, c_max, c_mean, c_median)

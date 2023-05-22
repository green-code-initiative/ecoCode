
def testImage(image) :
    return "path/to/" + image


def testImageFormat2() :

    img_svg = "test/image.svg"                    # Compliant

    image_format = testImage("image.svg")         # Compliant

    image_svg_html = ('<html><svg width="100" height="100">' +  # Compliant
                      '<circle cx="50" cy="50" r="40" stroke="green" stroke-width="4" fill="yellow" />' +
                      '</svg></html>')

    return ('<html><img src="xx/xx/image.svg" >'  # Compliant
            + '</html>' )

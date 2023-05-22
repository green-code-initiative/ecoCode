
def testImage(image) :
    return "path/to/" + image


def testImageFormat2() :

    img_bmp = "test/image.bmp"                    # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
    img_ico = "image.ico"                         # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
    img_tiff = "test/path/to/image.tiff"          # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
    img_webp = "test/path/to/" + "image.webp"     # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
    img_jpg = "image.jpg"                         # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
    img_jpeg = "image.jpeg"                       # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
    img_jfif = "image.jfif"                       # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
    img_pjpeg = "image.pjpeg"                     # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
    img_pjp = "image.pjp"                         # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
    img_gif = "image.gif"                         # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
    img_avif = "image.avif"                       # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
    img_apng = "image.apng"                       # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}

    image_format = testImage("image.jpg")         # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}

    return ('<html><img src="xx/xx/image.bmp" >' # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
            + '<img src="xx/xx/image.ico" >'     # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
            + '<img src="xx/xx/image.tiff" >'    # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
            + '<img src="xx/xx/image.webp" >'    # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
            + '<img src="xx/xx/image.png" >'     # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
            + '<img src="xx/xx/image.jpg" >'     # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
            + '<img src="xx/xx/image.jpeg" >'    # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
            + '<img src="xx/xx/image.jfif" >'    # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
            + '<img src="xx/xx/image.pjpeg" >'   # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
            + '<img src="xx/xx/image.pjp" >'     # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
            + '<img src="xx/xx/image.gif" >'     # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
            + '<img src="xx/xx/image.avif" >'    # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
            + '<img src="xx/xx/image.apng" >'    # Noncompliant {{If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.}}
            + '</html>' )

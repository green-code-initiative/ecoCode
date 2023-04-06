image0 = """<svg xmlns:svg="http://www.w3.org/2000/svg"><g></g></svg>"""
image1 = """<svg xmlns:inkscape="http://www.inkscape.org/namespaces/inkscape"></svg>""" # Noncompliant {{Avoid using unoptimized vector images}}
image2 = "<svg><!-- Hello world --></svg>" # Noncompliant {{Avoid using unoptimized vector images}}
image3 = "<svg><g>...</g><g>...</g></svg>" # Noncompliant {{Avoid using unoptimized vector images}}
image4 = "<svg><metadata></metadata></svg>" # Noncompliant {{Avoid using unoptimized vector images}}

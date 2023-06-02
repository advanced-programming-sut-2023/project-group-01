from captcha.image import ImageCaptcha
n="38978"
image = ImageCaptcha()
data = image.generate(str(n))
image.write(str(n), str(n) + '.png')
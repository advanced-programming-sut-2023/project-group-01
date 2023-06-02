from captcha.image import ImageCaptcha
n="68241"
image = ImageCaptcha()
data = image.generate(str(n))
image.write(str(n), str(n) + '.png')
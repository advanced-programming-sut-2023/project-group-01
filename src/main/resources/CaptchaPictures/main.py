from captcha.image import ImageCaptcha
n="39557"
image = ImageCaptcha()
data = image.generate(str(n))
image.write(str(n), str(n) + '.png')